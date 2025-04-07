package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response
import com.g5.util.GloveLoader
import java.util.PriorityQueue

class ResponseService {
    companion object {
        private const val MAX_NUM_TRIES = 3
        private const val NO_IMAGE_LINK = "link unavailable"
        private const val NO_SYMBOL = "Could not identify"
        private const val NO_CONTEXT = "No context available"
    }

    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private var knowledge: String = "none"

    private var input: String = ""
    private var imageLink: String = ""
    private var isImage: Boolean = false

    fun addExpert(expert: ExpertInterface) {
        experts.add(expert)
    }

    fun getExpertCount(): Int {
        return experts.size
    }

    fun submitPrompt(prompt: Prompt) {
        input = prompt.getInput()
        imageLink = prompt.getImageLink() ?: NO_IMAGE_LINK
        isImage = imageLink != NO_IMAGE_LINK
    }

    suspend fun generateResponse(): Response {
        var finalId: String = NO_SYMBOL
        var tries: Int = 1

        while (tries < MAX_NUM_TRIES) {
            println(">> using experts")
            val expertResponses = useExperts(generateExpertInput())
            val mergedResponses = mergeSimilarResponses(expertResponses)

            finalId = mergedResponses.firstOrNull() ?: finalId
            if (mergedResponses.size == 1) break

            knowledge = mergedResponses.joinToString("; ")
            tries += 1;
        }
        println(">> final response: $finalId")

        val formattedResponse: Response = Response()
        formattedResponse.setSymbol(finalId)
        if (finalId != NO_SYMBOL) formattedResponse.setContext(contextFor(finalId))

        return formattedResponse
    }

    // expert helpers
    private fun generateExpertInput(): String {
        return (if (isImage) (imageLink) else input) + ", with additional background information that other guesses are (in order of likelihood)... " + knowledge
    }
    
    private suspend fun useExperts(input: String): MutableList<String> {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String? = expert.generateResponse(input, isImage)
            res?.let { expertRes.add(it) }
        }

        return expertRes
    }

    // semantic helpers
    fun mergeSimilarResponses(strings: List<String>): List<String> {
        println(">> merging responses")

        val mergeCounts = mutableMapOf<String, Int>()
        val visited = BooleanArray(strings.size)

        // count number of alike strings per "source"
        for (i in strings.indices) {
            if (visited[i]) continue
            val vecA = phraseToVector(strings[i]) ?: continue

            for (j in i + 1 until strings.size) {
                if (visited[j]) continue
                val vecB = phraseToVector(strings[j]) ?: continue

                if (cosineSimilarity(vecA, vecB) >= 0.7) {
                    mergeCounts[strings[i]] = (mergeCounts[strings[i]] ?: 0) + 1
                    visited[j] = true
                }
            }
        }

        // add all "sources", ordered by number of alike strings
        val pq = PriorityQueue<String> { a, b -> 
            (mergeCounts[b] ?: 0) - (mergeCounts[a] ?: 0)
        }

        pq.addAll(mergeCounts.keys)

        // include "loners", who did not have any alike strings
        strings.forEachIndexed { i, str ->
            if (!visited[i] && str !in mergeCounts) {
                pq.add(str)
            }
        }

        return generateSequence { pq.poll() }.toList()
    }


    private fun phraseToVector(phrase: String): List<Double> {
        val words = phrase.lowercase().split(Regex("\\W+")).filter { it.isNotBlank() }
        val summed = DoubleArray(50)
        var validVectorsCount = 0

        for (word in words) {
            val vector = GloveLoader.getVectorForWord(word)
            if (vector != null) {
                vector.indices.forEach { i -> 
                    summed[i] += vector[i]
                }
                validVectorsCount++
            }
        }

        if (validVectorsCount == 0) {
            println(">> no vectors found for phrase: $phrase")
            return emptyList()
        }

        return summed.map { it / validVectorsCount }
    }

    private fun cosineSimilarity(v1: List<Double>, v2: List<Double>): Double {
        println(">> calculating cosine similarity")

        if (v1.isEmpty() || v2.isEmpty()) return 0.0

        var dot = 0.0
        var norm1Squared = 0.0
        var norm2Squared = 0.0

        for (i in v1.indices) {
            dot += v1[i] * v2[i]
            norm1Squared += v1[i] * v1[i]
            norm2Squared += v2[i] * v2[i]
        }

        val norm1 = Math.sqrt(norm1Squared)
        val norm2 = Math.sqrt(norm2Squared)

        return if (norm1 == 0.0 || norm2 == 0.0) 0.0 else dot / (norm1 * norm2)
    }

    // context helpers
    private suspend fun contextFor(symbol: String): String {
        if (symbol == NO_SYMBOL || experts.isEmpty()) return NO_CONTEXT
        // TODO: should NOT instantiate gemini each time... ideally have one instance shared across all
        return GeminiService.askGemini("Concisely describe background context for this symbol: $symbol") ?: NO_CONTEXT
    }
}
