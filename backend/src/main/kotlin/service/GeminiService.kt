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

class GeminiService {
    companion object {
        const val GEMINI_API_KEY = "AIzaSyCKocdJqOPdZ7L73-8A5EQJqZN5HORCQzM"
        const val URL_STRING_CLIENT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$GEMINI_API_KEY"
    }

    suspend fun askGemini(input: String, imgurUrl: String? = null): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                jackson()
            }
        }
        return try {
            val response: HttpResponse = client.post(URL_STRING_CLIENT) {
                headers {
                    // API Limit: 1500 RPD 15 RPM
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
                setBody(
                    mapOf(
                        "contents" to listOf(
                            mapOf(
                                "parts" to if (imgurUrl == null) {
                                    listOf(mapOf("text" to input))
                                } else {
                                    listOf(
                                        mapOf("text" to input),
                                        mapOf(
                                            "inline_data" to mapOf(
                                                "mime_type" to "image/png", // Or image/jpeg, etc.
                                                "data" to convertImageToBase64(client.get(imgurUrl).body())
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
        } finally {
            client.close()
        }
    }

    private fun convertImageToBase64 (imgBytes: ByteArray): String? {
        return Base64.getEncoder().encodeToString(imgBytes)
    }
}