package com.g5.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.call.*
import java.util.Base64

object GeminiService {
    private const val GEMINI_API_KEY = "AIzaSyCKocdJqOPdZ7L73-8A5EQJqZN5HORCQzM"
    private const val URL_STRING_CLIENT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$GEMINI_API_KEY"

    private val client: HttpClient by lazy {
        println("success: Gemini client initialized!")
        HttpClient(CIO) {
            install(ContentNegotiation) {
                jackson()
            }
        }
    }

    fun initGeminiClient() {
        client
    }

    suspend fun askGemini(input: String, imgurUrl: String? = null): String {
        return try {
            val activeClient = client ?: error("Gemini client not initialized. Call initGeminiClient() first.")
            val trueUrl = extractFirstImageLink(imgurUrl)

            val response: HttpResponse = activeClient.post(URL_STRING_CLIENT) {
                headers {
                    // API Limit: 1500 RPD 15 RPM
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(
                    mapOf(
                        "contents" to listOf(
                            mapOf(
                                "parts" to if (trueUrl == null) {
                                    listOf(mapOf("text" to input))
                                } else {
                                    listOf(
                                        mapOf("text" to input),
                                        mapOf(
                                            "inline_data" to mapOf(
                                                "mime_type" to "image/png", // Or image/jpeg, etc.
                                                "data" to convertImageToBase64(client.get(trueUrl).body())
                                            )
                                        )
                                    )
                                }
                            )
                        )
                    )
                )
            }
            val json = jacksonObjectMapper().readTree(response.bodyAsText())
            return json["candidates"]?.get(0)?.get("content")?.get("parts")?.get(0)?.get("text")?.asText()
                ?: "Request failed"
        } catch (e: Exception) {
            println("Request failed: ${e.message}")
            "Request failed"
        }
    }

    private fun convertImageToBase64(imgBytes: ByteArray): String? {
        return Base64.getEncoder().encodeToString(imgBytes)
    }

    private fun extractFirstImageLink(inputString: String?): String? {
        val input = inputString ?: return null
        val regex = "https://i\\.imgur\\.com/[a-zA-Z0-9]+\\.(png|jpeg|jpg|gif)".toRegex()
        val match = regex.find(input)
        return match?.value
    }
}