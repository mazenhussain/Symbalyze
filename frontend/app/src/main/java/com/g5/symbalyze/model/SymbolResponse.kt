package com.g5.symbalyze.model

import java.util.UUID

data class SymbolResponse(
    val id: UUID, // double check....
    val name: String,
    val context: String
)
