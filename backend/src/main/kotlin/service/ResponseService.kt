package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response

class ResponseService {
    companion object {
        private const val MAX_NUM_TRIES = 3
        private const val DEFAULT_SYMBOL = "Could not identify"
        private const val DEFAULT_CONTEXT = "No context available"
    }

    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private var input: String = ""

    fun addExpert(expert: ExpertInterface) {
        experts.add(expert)
    }

    fun getExpertCount(): Int {
        return experts.size
    }

    fun submitPrompt(prompt: Prompt) {
        // TODO: format prompt object into string

        input = "Not implemented yet"
    }

    suspend fun generateResponse(): Response {
        var acceptable: Boolean = false
        var finalId: String = DEFAULT_SYMBOL
        var tries: Int = 0

        while (!acceptable && tries < MAX_NUM_TRIES) {
            val newId: String = useExperts(input)
            acceptable = isSatisfactory(newId)

            if (acceptable) {
                finalId = newId
                break
            } else {
                tries += 1
                //updateExperts(newId)
            }
        }

        val formattedResponse: Response = Response()
        formattedResponse.setSymbol(finalId)
        if (finalId != DEFAULT_SYMBOL) formattedResponse.setContext(contextFor(finalId))

        return formattedResponse
    }

    private suspend fun useExperts(input: String): String {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String? = expert.generateResponse(input) // Unsure
            res?.let { expertRes.add(it) }
        }

        return mergeResponses(expertRes)
    }

    private fun mergeResponses(responses: List<String>): String {
        if (responses.isEmpty()) return DEFAULT_SYMBOL

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

//    private suspend fun updateExperts(knowledge: String) {
//        for (expert in experts) {
//            expert.updateKnowledge(knowledge)
//        }
//    }

    private suspend fun contextFor(symbol: String): String {
        val expert = experts.firstOrNull() ?: return DEFAULT_CONTEXT
        val knowledge = "Ignore everything else. Please concisely describe background context for: $symbol" // DISCUSS: should we have a "clearKnowledge" method added to expert interface?
        return expert.generateResponse(knowledge) ?: DEFAULT_CONTEXT // Unsure
    }
}
