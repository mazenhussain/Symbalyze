package com.g5.service

import com.g5.service.PromptService
import com.g5.model.Prompt
import kotlinx.coroutines.*

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PromptServiceTest {
    private lateinit var promptService: PromptService
    private final val DUMMY_BASE64: String = "iVBORw0KGgoAAAANSUhEUgAAAAUA"

    @Before
    fun setUp() {
        promptService = PromptService()
    }

    @Test
    fun `prompt can be created without image`() {
        val input = "Test input"
        val prompt = promptService.createPrompt(input, null)

        assertEquals(input, prompt.getInput())
        assertNull(prompt.getImageLink())
    }

    @Test
    fun `prompt can be created with image`() {
        val input = "Test input"
        val imageLink = "https://someimgurl.com/image.jpg"
        val prompt = promptService.createPrompt(input, imageLink)

        assertEquals(input, prompt.getInput())
        assertEquals(imageLink, prompt.getImageLink())
    }

    @Test
    fun `accessible image link is returned`() = runBlocking {
        val base64Image = "data:image/jpeg;base64,$DUMMY_BASE64"
        val imageLink = promptService.uploadToImgur(base64Image)
        assertTrue(imageLink.isNotEmpty())
    }
}
