package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response

class ResponseService {
    companion object {
        private const val MAX_NUM_TRIES = 3
        private const val NO_SYMBOL = "Could not identify"
        private const val NO_CONTEXT = "No context available"
    }

    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private val knowledge: MutableList<String> = mutableListOf()
    private val input: String = ""
    private val isImage: Boolean = false

    fun addExpert(expert: ExpertInterface) {
        experts.add(expert)
    }

    fun getExpertCount(): Int {
        return experts.size
    }

    fun submitPrompt(prompt: Prompt) {
        // TODO: format prompt object into string

        input = "Not implemented yet"
        isImage = false
    }

    suspend fun generateResponse(): Response {
        var acceptable: Boolean = false
        var finalId: String = NO_SYMBOL
        var tries: Int = 0

        while (!acceptable && tries < MAX_NUM_TRIES) {
            val newId: String = useExperts("Identify the symbol given: " + input + ". Additional information is as follows: " + knowledge.joinToString(","))
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

    private suspend fun useExperts(input: String): String {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String? = expert.generateResponse(input, isImage)
            res?.let { expertRes.add(it) }
        }

        return mergeResponses(expertRes)
    }

    // TODO: MAKE THIS BETTER
    private fun mergeResponses(responses: List<String>): String {
        if (responses.isEmpty()) return NO_SYMBOL

        val phraseCounts = mutableMapOf<String, Int>()

        for (response in responses) {
            val normalizedResponse = response.trim().lowercase()
            phraseCounts[normalizedResponse] = phraseCounts.getOrDefault(normalizedResponse, 0) + 1
        }

        return phraseCounts.entries
            .sortedByDescending { it.value }
            .joinToString(", ") { it.key }
    }

    private fun isSatisfactory(response: String): Boolean {
        val phrases = response.split(", ").map { it.trim() }.toSet()
        return phrases.size == 1
    }

    private suspend fun contextFor(symbol: String): String {
        val expert = experts.firstOrNull() ?: return NO_CONTEXT
        val knowledge = "Please concisely describe background context for: $symbol"
        return expert.generateResponse(knowledge) ?: NO_CONTEXT
    }
}
