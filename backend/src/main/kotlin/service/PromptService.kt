package com.g5.service

import com.g5.model.Prompt

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.jackson.*

import kotlinx.coroutines.*
import java.io.File
import java.util.Base64
import org.json.JSONObject

class PromptService {
    companion object {
        const val IMGUR_CLIENT_ID = "d52bd5f6dec8e07"
        const val IMGUR_UPLOAD_URL = "https://api.imgur.com/3/upload"
        const val MEDIA_TYPE = "image/jpeg"
    }

    fun processPrompt(input: String, base64: String?): Prompt {
        val imageLink = base64?.let { runBlocking {
            uploadToImgur(it)
        } }
        return createPrompt(input, imageLink)
    }

    fun createPrompt(input: String, imageLink: String?): Prompt {
        val prompt = Prompt()
        prompt.setInput(input)
        prompt.setImageLink(imageLink)
        return prompt
    }

    suspend fun uploadToImgur(base64Image: String): String {
        val decodedBytes = Base64.getDecoder().decode(base64Image.replace("data:image/\\w+;base64,".toRegex(), ""))

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                jackson()
            }
        }

        try {
            val response: HttpResponse = client.submitFormWithBinaryData(
                url = IMGUR_UPLOAD_URL,
                formData = formData {
                    append("image", decodedBytes, Headers.build {
                        append(HttpHeaders.ContentType, MEDIA_TYPE)
                    })
                }) {
                headers {
                    // NOTE: there's any API rate limit, so please only uncomment this for demo :)
                    // append(HttpHeaders.Authorization, "Client-ID $IMGUR_CLIENT_ID")
                }
            }

            val jsonResponse = response.bodyAsText()
            println("Imgur API response: $jsonResponse")
            val json = JSONObject(jsonResponse)
            return json.getJSONObject("data").getString("link")
        } catch (e: Exception) {
            println("Upload failed: ${e.message}")
            return "Upload failed"
        } finally {
            client.close()
        }
    }
}
