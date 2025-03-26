package com.g5.model

class Response {
    private var symbol: String = ""
    private var context: String = ""
    private var confidence: Int = 0

    fun setSymbol(identifiedSymbol: String) {
        symbol = identifiedSymbol
    }

    fun getSymbol(): String {
        return symbol
    }

    fun setContext(foundContext: String) {
        context = foundContext
    }

    fun getContext(): String {
        return context
    }

    fun setConfidence(level: Int) {
        confidence = level
    }
}
