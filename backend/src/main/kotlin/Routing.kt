package com.g5

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*

import com.g5.model.IdentifyRequest
import com.g5.model.Prompt
import com.g5.model.Response

import com.g5.service.ResponseService

fun Application.configureRouting() {
    val responseService = ResponseService()

    install(ContentNegotiation) {
        jackson()
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/identify-symbol") {
            try {
                val requestBody = call.receive<IdentifyRequest>()
                val processedPrompt = Prompt() // TODO: call prompt service to process input and upload base64 string to bucket

                val response = responseService.submitPrompt(processedPrompt)
                val generatedResponse = responseService.generateResponse()
                
                call.respond(mapOf("symbol" to generatedResponse.getSymbol(), "context" to generatedResponse.getContext()))
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
