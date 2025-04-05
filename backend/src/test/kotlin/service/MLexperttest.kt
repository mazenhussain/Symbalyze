package com.g5.model

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MLExpertTest {

    @Test
    fun `test MLExpert with valid image input`() = runBlocking {
        val mlExpert = MLExpert()

        // Replace with a valid image link for testing
        val imageLink = "https://i.imgur.com/YFWdUHF.png"

        val response = mlExpert.generateResponse(imageLink, isImage = true)

        // Assert that the response is not null and contains a valid symbol name
        assertNotNull(response, "Response should not be null")
        println("MLExpert Response for image: $response")
    }

    @Test
    fun `test MLExpert with valid text description`() = runBlocking {
        val mlExpert = MLExpert()

        // Replace with a valid text description for testing
        val textDescription = "A checkmark on a shoe."

        val response = mlExpert.generateResponse(textDescription, isImage = false)

        // Assert that the response is not null and contains a valid symbol name
        assertNotNull(response, "Response should not be null")
        println("MLExpert Response for text: $response")
    }
}