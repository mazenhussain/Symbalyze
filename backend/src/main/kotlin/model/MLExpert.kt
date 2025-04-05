package com.g5.model

import com.g5.service.GeminiService
import com.g5.service.PromptService

class MLExpert : ExpertInterface {

    private val geminiService = GeminiService()
    private val promptService = PromptService()

    override suspend fun generateResponse(input: String, isImage: Boolean?): String? {
        return try {
            // Ensure the input is an image
            if (isImage != true) {
                return "Error: This expert only works with image inputs."
            }

            // Validate the input URL
            if (!isValidUrl(input)) {
                return "Error: Invalid image URL provided."
            }

            // Create a prompt for Gemini using the input image
            val prompt = createPrompt(input)
            println("Generated Prompt: ${prompt.getInput()} with ImageLink: ${prompt.getImageLink()}")

            // Use GeminiService to get a response from the Gemini API
            val geminiResponse = geminiService.askGemini(prompt.getInput(),prompt.getImageLink())
            println("Gemini API Response: $geminiResponse")

            // Extract the symbol name from the Gemini API response
            val symbolName = extractSymbolName(geminiResponse)

            symbolName ?: "No matching symbol found"
        } catch (e: Exception) {
            println("Error in MLExpert: ${e.message}")
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

    private fun createPrompt(imageLink: String): Prompt {
        return try {
            // Create a Prompt object with both input and imageLink
            val prompt = promptService.createPrompt(
                input = "Identify the name of the symbol in the image. Only return the name of the symbol and nothing else.",
                imageLink = imageLink
            )
            prompt
        } catch (e: Exception) {
            println("Error creating prompt: ${e.message}")
            Prompt().apply {
                setInput("Error: Unable to create prompt.")
            }
        }
    }

    private fun extractSymbolName(response: String): String? {
        if (response.isBlank()) {
            println("Error: Empty response from Gemini API.")
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