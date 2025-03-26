package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response

class ResponseService {
    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private var input: String = ""

    fun addExpert(expert: ExpertInterface) {
        experts.add(expert)
    }

    fun submitPrompt(prompt: Prompt) {
        // TODO: format prompt object into string

        input = "Not implemented yet"
    }

    suspend fun generateResponse(): Response {
        var acceptable: Boolean = false
        var finalId: String = "Could not identify"

        while (!acceptable) {
            val newId: String = useExperts(input)
            acceptable = isSatisfactory(newId)

            if (acceptable) {
                finalId = newId
                break
            } else {
                updateExperts(newId)
            }
        }

        val formattedResponse: Response = Response()
        formattedResponse.setSymbol(finalId)
        formattedResponse.setContext(contextFor(finalId))

        return formattedResponse
    }

    private suspend fun useExperts(input: String): String {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String? = expert.generateResponse()
            res?.let { expertRes.add(it) }
        }

        return mergeResponses(expertRes)
    }

    private fun mergeResponses(responses: List<String>): String {
        if (responses.isEmpty()) return "No answer found"

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
        // criteria here is that we have converged on a singular phrase
        val phrases = response.split(", ").map { it.trim() }.toSet()
        return phrases.size == 1
    }

    private suspend fun updateExperts(knowledge: String) {
        for (expert in experts) {
            expert.updateKnowledge(knowledge)
        }
    }

    private suspend fun contextFor(symbol: String): String {
        val expert = experts.firstOrNull() ?: return "No context available"
        val knowledge = "Ignore everything else. Please concisely describe background context for: $symbol"
        expert.updateKnowledge(knowledge)
        return expert.generateResponse() ?: "No context available"
    }
}
