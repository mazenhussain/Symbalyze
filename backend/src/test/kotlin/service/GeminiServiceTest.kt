package service

import com.g5.service.GeminiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

// COMMENTED OUT DUE TO INFRA CHANGES
// class GeminiServiceTest {
//     private lateinit var geminiService: GeminiService
//     private final val TEST_INPUT: String = "Tell me about the Gemini AI Model."
//     private final val TEST_INPUT_IMAGE: String = "Describe the following image..."
//     private final val TEST_IMAGE_LINK: String = "https://i.imgur.com/YFWdUHF.png"

//     @Before
//     fun setUp() {
//         geminiService = GeminiService()
//     }

//     @Test
//     fun `Response from API is successful`() = runBlocking {
//         val response = geminiService.askGemini(TEST_INPUT)
//         println("Response:" + response)
//         assertNotEquals("Request failed", response)
//     }

//     @Test
//     fun `Response from API is successful Images`() = runBlocking {
//         val response = geminiService.askGemini(TEST_INPUT_IMAGE, TEST_IMAGE_LINK)
//         println("Response:" + response)
//         assertNotEquals("Request failed", response)
//     }
// }