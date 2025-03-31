package com.g5.model

interface ExpertInterface {
    suspend fun generateResponse(input: String, isImage: boolean? = false): String?
}
