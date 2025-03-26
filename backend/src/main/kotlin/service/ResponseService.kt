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

    private fun useExperts(input: String): String {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String = expert.generateResponse()
            res?.let { expertRes.add(it) }
        }

        return mergeResponses(expertRes)
    }

    private fun updateExperts(knowledge: String) {
        for (expert in experts) {
            expert.updateKnowledge(knowledge)
        }
    }

    private fun isSatisfactory(response: String): Boolean {
        return true // TODO: idk
    }

    private fun mergeResponses(responses: List<String>): String {
        return "Not implemented yet" // TODO: determine if there's a conflict, and if so, pick which takes precedence
    }

    private suspend fun contextFor(symbol: String): String {
        return "Not implemented yet" // TODO: idk search google fire emoji
    }
}
