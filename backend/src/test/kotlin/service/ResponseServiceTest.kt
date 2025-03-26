package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Response

import io.mockk.*
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ResponseServiceTest {
    private lateinit var responseService: ResponseService
    private lateinit var expert1: ExpertInterface
    private lateinit var expert2: ExpertInterface

    @Before
    fun setUp() {
        responseService = ResponseService()
        expert1 = mockk()
        expert2 = mockk()
    }

    @Test
    fun `submitPrompt should call experts with string format of prompt`() {
        // TODO: implement once available
    }

    @Test
    fun `addExpert should store experts in the list`() {
        responseService.addExpert(expert1)
        responseService.addExpert(expert2)

        assertEquals(2, responseService.getExpertCount())
    }

    @Test
    fun `generateResponse should return a unanimous response`() = runBlocking {
        coEvery { expert1.generateResponse() } returns "New Balance"
        coEvery { expert1.updateKnowledge(any()) } just Runs

        coEvery { expert2.generateResponse() } returns "New Balance"
        coEvery { expert2.updateKnowledge(any()) } just Runs

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2)

        val response = responseService.generateResponse()

        assertEquals("new balance", response.getSymbol())
    }

    @Test
    fun `generateResponse should only try a maximum of 3 times`() = runBlocking {
        coEvery { expert1.generateResponse() } returns "Nike"
        coEvery { expert1.updateKnowledge(any()) } just Runs

        coEvery { expert2.generateResponse() } returns "Adidas"
        coEvery { expert2.updateKnowledge(any()) } just Runs

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2) 

        val response = responseService.generateResponse()

        coVerify(exactly = 3) { expert1.generateResponse() }
        coVerify(exactly = 3) { expert1.updateKnowledge(any()) }
    }

    @Test
    fun `generateResponse should acquire context for final response`() = runBlocking {
        coEvery { expert1.generateResponse() } returns "Nike" andThen "Some facts about Nike"
        coEvery { expert1.updateKnowledge(any()) } just Runs

        coEvery { expert2.generateResponse() } returns "Nike"
        coEvery { expert2.updateKnowledge(any()) } just Runs

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2)

        val response = responseService.generateResponse()

        assertEquals("Some facts about Nike", response.getContext())
    }
}
