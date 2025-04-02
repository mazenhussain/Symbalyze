package com.g5.model

data class Symbol(
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val history: String = "",
    val url: String = "",
    val tags: List<String> = emptyList()
)
