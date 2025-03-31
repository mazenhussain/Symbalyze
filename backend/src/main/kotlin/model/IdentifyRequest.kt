package com.g5.model

data class IdentifyRequest(
    val input: String,
    val base64: String? = null
)