package service

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertNotNull
import com.g5.service.WebExpert
import java.util.logging.Logger

class WebExpertTest {
    @Test
    fun `test WebExpert with valid image URL`() = runBlocking {
        val webExpert = WebExpert()
        val logger = Logger.getLogger("TestLogger")
        
        val imageUrl = "https://brandlogos.net/wp-content/uploads/2020/11/nike-swoosh-logo-512x512.png"

        val response = webExpert.generateResponse(imageUrl, isImage = true)

        assertNotNull(response, "Response should not be null")
        logger.info("WebExpert Response: $response")
    }

    @Test
    fun `test WebExpert with text description`() = runBlocking {
        val webExpert = WebExpert()
        val logger = Logger.getLogger("TestLogger")

        val textDescription = "check swoosh"

        val response = webExpert.generateResponse(textDescription, isImage = false)

        assertNotNull(response, "Response should not be null")
        logger.info("WebExpert Response: $response")
    }
}