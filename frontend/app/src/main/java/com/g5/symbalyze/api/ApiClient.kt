package com.g5.symbalyze.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// concrete api clients we can call
object UserApiClient {
    private const val BASE_URL = "https://some-api.com/" // TODO: replace with our backend url

    val apiService: UserApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiInterface::class.java)
    }
}

object SymbolApiClient {
    private const val BASE_URL = "https://some-api.com/" // TODO: replace with our backend url

    val apiService: SymbolApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SymbolApiInterface::class.java)
    }
}