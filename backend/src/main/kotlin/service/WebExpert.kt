package com.g5.service

import com.g5.model.ExpertInterface
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class WebExpert : ExpertInterface {

    // private val geminiService = GeminiService()
    // private val promptService = PromptService()
    private val client = OkHttpClient()

    private val SERP_API_KEY = "7d4b9ad0b80819fe33fd6212667fa3033438071cff092737f8cf8902ba749750"

    override suspend fun generateResponse(input: String, isImage: Boolean?): String? {
        return try {
            val visualDescription =
                if (isImage == true && isValidUrl(input)) {
                    GeminiService.askGemini("Describe this logo in a sentence.", input)
                } else {
                    input
                }
                // if the input was an image, gemini will describe it as a sentence and if not it will just be the user text description

            println("Visual Description: $visualDescription")
            // priniting the visual description of the iamge/text input

            val searchResults = getSearchSnippets(visualDescription)
            println("Top 5 search snippets:\n$searchResults")
            // only scraping the first 5 links that pop up on google 

            val summaryPrompt =
                "From the following search result snippets: \"$searchResults\", extract the single most likely brand or symbol name being described. Only return the name."

            val symbol = GeminiService.askGemini(summaryPrompt)
            // Gemini will then use that list of snippets to produce a related logo/symbol name 

            println("Symbol extracted: $symbol")
            symbol
        } catch (e: Exception) {
            println("Error in WebExpert: ${e.message}")
            "Error extracting symbol"
        }
    }

    // This function is used to filter the 5 snippets 
    private fun getSearchSnippets(query: String): String {
        val url =
            "https://serpapi.com/search?q=${query.replace(" ", "+")}&engine=google&api_key=$SERP_API_KEY"

        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: return "No context available"

        val json = JSONObject(body)
        val results = json.optJSONArray("organic_results") ?: return "No results"
        val titles =
            (0 until minOf(5, results.length())).mapNotNull {
                results.optJSONObject(it)?.optString("title")
            }

        return titles.joinToString(" ")
    }

    // This function checks the prefix of the input to check if its a valid url and not just a text input
    private fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
    }
}
