package com.g5.symbalyze.api

import android.util.Log
import com.g5.symbalyze.model.SymbolRequest
import com.g5.symbalyze.model.SymbolResponse

// modularized methods we'll actually use in ui, instead of directly calling from client
suspend fun identifySymbol(inputDesc: String? = null, inputImgBase64: String? = null): SymbolResponse? {
    val formattedRequest = SymbolRequest(
        input = inputDesc,
        base64 = inputImgBase64
    )

    return try {
        Log.d("debug", "calling backend with: " + formattedRequest.toString())
        val response = SymbolApiClient.apiService.identifySymbol(formattedRequest)
        Log.d("debug", "response received" + response.toString())
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    } catch (e: Exception) {
        Log.d("debug", "error accessing server: " + e.toString())
        null
    }
}
