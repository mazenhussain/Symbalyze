package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Prompt

class ResponseService {
    private val experts: MutableList<ExpertInterface> = mutableListOf()
    private var input: String

    fun addExpert(expert: ExpertInterface) {
        experts.add(expert)
    }

    fun submitPrompt(prompt: Prompt) {
        // TODO: format prompt object into string, then set that to the input attribute

        input = "Not implemented yet"
    }

    suspend fun generateResponse(input: String): String? {
        val expertRes: MutableList<String> = mutableListOf()

        for (expert in experts) {
            val res: String = useExpert(expert, input)
            if (res != null) expertRes.add(res)
        }

        val finalRes: String = mergeResponses(expertRes)

        // TODO: find a way to determine if this is a satisfactory response, and if not, update expert informations using their answers and repeat

        return finalRes
    }

    private fun useExpert(expert: ExpertInterface, input: String): String? {
        return expert.generateResponse(input)
    }

    private fun mergeResponses(val responses: List<String>): String {
        return "Not implemented yet"
    }
}
