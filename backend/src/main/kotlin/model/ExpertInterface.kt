package com.g5.model

interface ExpertInterface {
    suspend fun updateKnowledge(input: String)
    suspend fun generateResponse(): String?
}
