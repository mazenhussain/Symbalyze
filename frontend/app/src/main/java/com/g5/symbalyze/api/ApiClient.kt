package com.g5.symbalyze.api

import com.g5.symbalyze.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserApiClient {
    private const val BASE_URL = BuildConfig.API_BASE_URL // NOTE: please set API_BASE_URL in local.properties

    val apiService: UserApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiInterface::class.java)
    }
}

object SymbolApiClient {
    private const val BASE_URL = BuildConfig.API_BASE_URL // NOTE: please set API_BASE_URL in local.properties

    val apiService: SymbolApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SymbolApiInterface::class.java)
    }
}