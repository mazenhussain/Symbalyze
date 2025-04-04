package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response

class ResponseService {
    companion object {
        private const val MAX_NUM_TRIES = 3
        private const val NO_IMAGE_LINK = "link unavailable"
        private const val NO_SYMBOL = "Could not identify"
        private const val NO_CONTEXT = "No context available"
    }

    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private val knowledge: MutableList<String> = mutableListOf()

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
        var acceptable: Boolean = false
        var finalId: String = NO_SYMBOL
        var tries: Int = 0

        while (!acceptable && tries < MAX_NUM_TRIES) {
            val newId: String = useExperts(generateExpertInput())
            acceptable = isSatisfactory(newId)

            if (acceptable) {
                finalId = newId
                break
            } else {
                tries += 1
                knowledge.add(newId)
            }
        }

        val formattedResponse: Response = Response()
        formattedResponse.setSymbol(finalId)
        if (finalId != NO_SYMBOL) formattedResponse.setContext(contextFor(finalId))

        return formattedResponse
    }

    private fun generateExpertInput(): String {
        if (knowledge.isEmpty()) {
            return (if (isImage) (imageLink) else input)
        } else {
            return (if (isImage) (imageLink) else input) + ", with additional background information: " + knowledge.joinToString(",")
        }
    }
    
    private suspend fun useExperts(input: String): String {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String? = expert.generateResponse(input, isImage)
            res?.let { expertRes.add(it) }
        }

        return mergeResponses(expertRes)
    }

    private fun mergeResponses(responses: List<String>): String {
        if (responses.isEmpty()) return NO_SYMBOL

        val stopWords = setOf("the", "a", "an", "of", "in", "to", "for", "on", "at", "by", "with", "about") 
        val wordCounts = mutableMapOf<String, Int>()
        val groupedResponses = mutableMapOf<Set<String>, MutableList<String>>()

        for (response in responses) {
            val words = response.lowercase()
                .split(Regex("\\W+"))
                .filter { it.isNotBlank() && it !in stopWords }
                .toSet()

            for (word in words) {
                wordCounts[word] = wordCounts.getOrDefault(word, 0) + 1
            }

            groupedResponses.computeIfAbsent(words) { mutableListOf() }.add(response)
        }

        val sortedWords = wordCounts.entries
            .sortedByDescending { it.value }
            .map { it.key }

        val mergedResponses = groupedResponses.entries.map { (wordSet, phrases) ->
            val mostFrequentWords = wordSet.filter { it in sortedWords.take(5) }
            val summary = mostFrequentWords.joinToString(" ") { it }
            "${phrases.distinct().joinToString(" / ")}"
        }

        return mergedResponses.joinToString("; ")
    }

    private fun isSatisfactory(response: String): Boolean {
        val phrases = response.split("; ").map { it.trim() }
        if (phrases.size == 1) return true

        val phraseCounts = mutableMapOf<String, Int>()
        val totalResponses = phrases.size

        for (phrase in phrases) {
            val words = phrase.split(Regex("\\W+")).map { it.trim().lowercase() }
            for (word in words) {
                phraseCounts[word] = phraseCounts.getOrDefault(word, 0) + 1
            }
        }

        val maxFrequency = phraseCounts.values.maxOrNull() ?: 0
        val agreementRatio = maxFrequency.toDouble() / totalResponses

        return agreementRatio >= 0.7
    }

    private suspend fun contextFor(symbol: String): String {
        if (symbol == NO_SYMBOL || experts.isEmpty()) return NO_CONTEXT
        return GeminiService().askGemini("Please concisely describe background context for this symbol: $symbol") ?: NO_CONTEXT
    }
}
