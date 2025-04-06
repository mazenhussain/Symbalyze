package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response
import com.g5.util.GloveLoader
import java.util.PriorityQueue

class ResponseService {
    companion object {
        private const val MAX_NUM_TRIES = 3
        private const val GLOVE_MODEL_PATH = "src/main/resources/largefiles/glove.6B.50d.txt"

        private const val NO_IMAGE_LINK = "link unavailable"
        private const val NO_SYMBOL = "Could not identify"
        private const val NO_CONTEXT = "No context available"
    }

    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private var knowledge: String = "none"
    private val glove: Map<String, List<Double>> by lazy {
        GloveLoader.loadGloveModel(GLOVE_MODEL_PATH)
    }

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
        var tries: Int = 0

        while (tries < MAX_NUM_TRIES) {
            println(">> using experts")
            val expertResponses = useExperts(generateExpertInput())
            val mergedResponses = mergeSimilarResponses(expertResponses)

            finalId = mergedResponses.get(0)
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
        return (if (isImage) (imageLink) else input) + ", with additional background information that other guesses (in order of likelihood)... " + knowledge
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
    fun mergeSimilarResponses(strings: MutableList<String>): List<String> {
        println(">> merging responses")
        val mergeCounts = mutableMapOf<String, Int>()
        
        val priorityQueue = PriorityQueue<String> { a, b ->
            mergeCounts[b]!! - mergeCounts[a]!!
        }

        strings.forEach { response ->
            mergeCounts[response] = 0
            priorityQueue.add(response)
        }

        var currentStrings = strings.toMutableList()
        var currentVectors = currentStrings.mapNotNull { phraseToVector(it) }

        var changed = true
        while (changed) {
            changed = false
            val newStrings = mutableListOf<String>()
            val newVectors = mutableListOf<List<Double>>()
            val used = BooleanArray(currentStrings.size)

            for (i in currentVectors.indices) {
                if (used[i]) continue
                var merged = currentStrings[i]
                var mergedVector = currentVectors[i]
                var mergedCount = mergeCounts[merged] ?: 0

                for (j in i + 1 until currentVectors.size) {
                    if (used[j]) continue
                    if (cosineSimilarity(mergedVector, currentVectors[j]) >= 0.7) {
                        merged += " or " + currentStrings[j]
                        mergedVector = phraseToVector(merged)!!
                        mergedCount = maxOf(mergedCount, mergeCounts[currentStrings[j]] ?: 0) + 1
                        used[j] = true
                        changed = true
                    }
                }

                used[i] = true
                newStrings.add(merged)
                newVectors.add(mergedVector)
                mergeCounts[merged] = mergedCount
                priorityQueue.add(merged)
            }

            currentStrings = newStrings
            currentVectors = newVectors
        }


        return if (priorityQueue.isNotEmpty()) {
            listOf(priorityQueue.poll())
        } else {
            strings
        }
    }

    private fun phraseToVector(phrase: String): List<Double> {
        val words = phrase.lowercase().split(Regex("\\W+")).filter { it.isNotBlank() }
        val vectors = words.mapNotNull { glove[it] }

        if (vectors.isEmpty()) println("no vector for $phrase")
        if (vectors.isEmpty()) return emptyList()

        val vectorLength = vectors[0].size
        val summed = DoubleArray(vectorLength)

        for (vec in vectors) {
            for (i in vec.indices) {
                summed[i] += vec[i]
            }
        }

        return summed.map { it / vectors.size }
    }

    private fun cosineSimilarity(v1: List<Double>, v2: List<Double>): Double {
        val dot = v1.zip(v2).sumOf { (a, b) -> a * b }
        val norm1 = Math.sqrt(v1.sumOf { it * it })
        val norm2 = Math.sqrt(v2.sumOf { it * it })

        return if (norm1 == 0.0 || norm2 == 0.0) 0.0 else dot / (norm1 * norm2)
    }

    // context helpers
    private suspend fun contextFor(symbol: String): String {
        if (symbol == NO_SYMBOL || experts.isEmpty()) return NO_CONTEXT
        // TODO: should NOT instantiate gemini each time... ideally have one instance shared across all
        return GeminiService().askGemini("Please concisely describe background context for this symbol: $symbol") ?: NO_CONTEXT
    }
}
