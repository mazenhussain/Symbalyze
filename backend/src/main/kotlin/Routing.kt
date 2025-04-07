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
import com.g5.service.PromptService
import com.g5.service.KeywordExpert
import com.g5.service.MLExpert
import com.g5.service.WebExpert

import com.g5.service.GeminiService
import com.g5.util.GloveLoader
import com.g5.util.FirebaseLoader

fun Application.configureRouting() {
    val promptService = PromptService()
    val responseService = ResponseService()

    // pre-load all singletons
    FirebaseLoader.initFirebase()
    GloveLoader.loadGloveModel()
    GeminiService.initGeminiClient()

    // subscribe experts
    responseService.addExpert(MLExpert())
    responseService.addExpert(KeywordExpert())
    responseService.addExpert(WebExpert())

    install(ContentNegotiation) {
        jackson()
    }

    routing {
        get("/") {
            call.respondText("hello from symbalyze :D")
        }

        post("/identify-symbol") {
            println("-------------------- received new request")
            try {
                val requestBody = call.receive<IdentifyRequest>()
                val processedPrompt = promptService.processPrompt(requestBody.input, requestBody.base64)

                val response = responseService.submitPrompt(processedPrompt)
                val generatedResponse = responseService.generateResponse()
                
                println("-------------------- returning response")
                call.respond(mapOf("symbol" to generatedResponse.getSymbol(), "context" to generatedResponse.getContext()))
            } catch (ex: Exception) {
                println("error processing request: " + ex)
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
