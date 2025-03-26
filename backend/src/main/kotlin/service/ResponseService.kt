package com.g5.service

import com.g5.model.ExpertInterface

class ResponseService {
    private val experts: MutableList<ExpertInterface> = mutableListOf()

    fun addExpert(expert: ExpertInterface) {
        experts.add(expert)
    }

    suspend fun generateResponse(input: String): String? {
        return "Not identified"
    }
}
