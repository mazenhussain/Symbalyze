package service

import com.g5.service.GeminiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class GeminiServiceTest {
    private lateinit var geminiService: GeminiService
    private final val TEST_INPUT: String = "Tell me about Gemini."

    @Before
    fun setUp() {
        geminiService = GeminiService()
    }

    @Test
    fun `Response from API is successful`() = runBlocking {
        val response = geminiService.askGemini(TEST_INPUT)
        println("Response:" + response)
        assertNotEquals("Request failed", response.isNotBlank())
    }
}