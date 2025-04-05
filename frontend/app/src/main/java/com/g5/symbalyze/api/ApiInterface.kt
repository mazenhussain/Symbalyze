package com.g5.symbalyze.api

import com.g5.symbalyze.model.SymbolRequest
import com.g5.symbalyze.model.SymbolResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiInterface {
    @GET("/save-symbol/{id}")
    suspend fun saveSymbol(@Path("id") symbolId: String)
}

interface SymbolApiInterface {
    @POST("/identify-symbol")
    suspend fun identifySymbol(@Body symbol: SymbolRequest): Response<SymbolResponse>
}
