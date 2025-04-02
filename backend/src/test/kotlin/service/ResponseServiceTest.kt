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
    fun `addExpert should store experts in the list`() {
        responseService.addExpert(expert1)
        responseService.addExpert(expert2)

        assertEquals(2, responseService.getExpertCount())
    }

    @Test
    fun `generateResponse should return a unanimous response`() = runBlocking {
        coEvery { expert1.generateResponse(any()) } returns "New Balance"
        coEvery { expert2.generateResponse(any()) } returns "New Balance"

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2)

        val response = responseService.generateResponse()

        assertEquals("New Balance", response.getSymbol())
    }

    @Test
    fun `generateResponse should only try a maximum of 3 times`() = runBlocking {
        coEvery { expert1.generateResponse(any()) } returns "Nike"
        coEvery { expert2.generateResponse(any()) } returns "Adidas"

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2) 

        val response = responseService.generateResponse()

        coVerify(exactly = 3) { expert1.generateResponse(any()) }
    }

    @Test
    fun `generateResponse should acquire context for final response`() = runBlocking {
        coEvery { expert1.generateResponse(any()) } returns "Nike" andThen "Some facts about Nike"
<<<<<<< HEAD

=======
>>>>>>> main
        coEvery { expert2.generateResponse(any()) } returns "Nike"

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2)

        val response = responseService.generateResponse()

        assertEquals("Some facts about Nike", response.getContext())
    }

    @Test
    fun `generateResponse should re-call experts with updated knowledge`() = runBlocking {
        coEvery { expert1.generateResponse(any()) } returns "Nike"
        coEvery { expert2.generateResponse(any()) } returns "Adidas" andThen "Nike"

        responseService.addExpert(expert1)  
        responseService.addExpert(expert2)

        val response = responseService.generateResponse()

        coVerifyOrder {
            expert1.generateResponse("")
            expert1.generateResponse(match { it.contains("background information") })
        }
    }
}
