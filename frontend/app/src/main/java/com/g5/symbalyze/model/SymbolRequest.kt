package com.g5.symbalyze.model

// this MUST match the backend, down to the type!
data class SymbolRequest(
    val input: String?,
    val base64: String?
)
