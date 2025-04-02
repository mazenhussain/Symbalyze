package com.g5.service

import com.g5.service.PromptService
import com.g5.model.Prompt
import kotlinx.coroutines.*

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PromptServiceTest {
    private lateinit var promptService: PromptService
    private final val TEST_INPUT: String = "This symbol is really red"
    private final val TEST_IMAGE_LINK: String = "https://someimgurl.com/image.jpg"
    private final val TEST_BASE64: String = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAWCAYAAADafVyIAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAHtSURBVEhLpZTfSxRxFMU/+0NtbLLNLRFs9yEWFR96Ecre6q1CCPrH+gOip96DHjIqeozIwCRFZbTAcLXVzZgV3cmZ+d4evsU643dmZ/W8DNz7nXMu51xuTkSEc2B1F54twLd96MvHu2Ao9YYrFlh9yURJ9cwoWXB5AAoJTAnl7LhQhFEb7H7AYPa5BETA9cAuglUEFX8A5M4acqigeQgfNuH9d9g9hFwu/uqMAoGCrRY8nYeVprYhKYOeBZTAp014Mg/HPvTnAcPk/9GTgOfDWweeL+lATZbEkVnA9WBuDV46EIbZyMm6Rb+P4J0DrzYg6IGcLAJuGz7+0ORtH/JZyEUvgpIuAm0flhvwZkNbVMhALqJDrwzBo8mUDJTA6h68WIbFn8lreBKhAnsAbo7ATBUmyikCjQN4vQ5z6yCqu++hgtFLcOs63KlAraztNM71J4CvDfhcBz9LqALVEtyvwew4jF/tZGUUqLdgYVt/TTf+JERgbAhmJ+DeDShfjPZP/X4cwpc6OM3u5EpgeFCT365o/+M4RbHdgrVfsO91sUagUICHNZge+3euDYgIKIGlHdhyoZhGDoQCd6t6W0pWvNtBRMBt6+vYPEqfPlBwbRAeTMKIHe9GERFw9mDnAALj4nbghfB4SoukDQLwF1J7uwgtHxZSAAAAAElFTkSuQmCC"

    @Before
    fun setUp() {
        promptService = PromptService()
    }

    @Test
    fun `prompt can be created without image`() {
        val prompt = promptService.createPrompt(TEST_INPUT, null)

        assertEquals(TEST_INPUT, prompt.getInput())
        assertNull(prompt.getImageLink())
    }

    @Test
    fun `prompt can be created with image`() {
        val prompt = promptService.createPrompt(TEST_INPUT, TEST_IMAGE_LINK)

        assertEquals(TEST_INPUT, prompt.getInput())
        assertEquals(TEST_IMAGE_LINK, prompt.getImageLink())
    }

    @Test
    fun `accessible image link is returned`() = runBlocking {
        val imageLink = promptService.uploadToImgur(TEST_BASE64)
        println("Image link:" + imageLink)
        assertTrue(imageLink.isNotEmpty())
    }
}
