package com.g5.service

import com.g5.service.GeminiService
import com.g5.service.PromptService
import com.g5.model.ExpertInterface
import com.g5.model.Prompt
import com.g5.model.Response
import org.slf4j.LoggerFactory

class MLExpert : ExpertInterface {
    private val promptService = PromptService()
    private val logger = LoggerFactory.getLogger(MLExpert::class.java)

    override suspend fun generateResponse(input: String, isImage: Boolean?): String? {
        return try {
            // Validate input
            if (input.isBlank()) {
                return "Error: Input cannot be empty."
            }

            // Automatically detect if the input is an image URL or a text description
            val isImageInput = isImage ?: isValidUrl(input)

            // Create a prompt based on the input type
            val prompt = if (isImageInput) {
                createPromptForImage(input)
            } else {
                createPromptForText(input)
            }

            logger.info("Generated Prompt: ${prompt.getInput()} with ImageLink: ${prompt.getImageLink()}")

            // Use GeminiService to get a response from the Gemini API
            val geminiResponse = GeminiService.askGemini(prompt.getInput(), prompt.getImageLink())
            logger.info("Gemini API Response: $geminiResponse")

            // Extract the symbol name from the Gemini API response
            val symbolName = extractSymbolName(geminiResponse)

            symbolName ?: "No matching symbol found"
        } catch (e: Exception) {
            logger.error("Error in MLExpert: ${e.message}", e)
            "An error occurred while processing your request. Please try again later."
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = java.net.URI(url)
            uri.scheme == "http" || uri.scheme == "https"
        } catch (e: Exception) {
            false
        }
    }

    private fun createPromptForImage(imageLink: String): Prompt {
        return try {
            promptService.createPrompt(
                input = "Identify the name of the symbol in the image. Only return the name of the symbol and nothing else.",
                imageLink = imageLink
            )
        } catch (e: Exception) {
            logger.error("Error creating prompt for image: ${e.message}", e)
            Prompt().apply {
                setInput("Error: Unable to create prompt for image.")
            }
        }
    }

    private fun createPromptForText(description: String): Prompt {
        return try {
            promptService.createPrompt(
                input = "Identify the name of the symbol based on the following description: \"$description\". Only return the name of the symbol and nothing else.",
                imageLink = null
            )
        } catch (e: Exception) {
            logger.error("Error creating prompt for text: ${e.message}", e)
            Prompt().apply {
                setInput("Error: Unable to create prompt for text.")
            }
        }
    }

    private fun extractSymbolName(response: String): String? {
        if (response.isBlank()) {
            logger.error("Error: Empty response from Gemini API.")
            return null
        }

        // Create a Response object and set the symbol
        val responseObject = Response()
        responseObject.setSymbol(response.trim()) // Assuming the response contains only the symbol name
        responseObject.setContext("Context not provided by Gemini.") // Placeholder context
        responseObject.setConfidence(100) // Assuming full confidence for now

        return responseObject.getSymbol()
    }
}