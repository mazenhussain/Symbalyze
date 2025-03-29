package com.g5.symbalyze.api

import com.g5.symbalyze.model.SymbolRequest
import com.g5.symbalyze.model.SymbolResponse

// modularized methods we'll actually use in ui, instead of directly calling from client
suspend fun identifySymbol(inputDesc: String? = null, inputImg: String? = null): SymbolResponse? {
    val formattedRequest = SymbolRequest(
        description = inputDesc,
        imageBase64 = inputImg
    )

    return try {
        val response = SymbolApiClient.apiService.identifySymbol(formattedRequest)
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}
