package service

import com.g5.model.ExpertInterface
import com.g5.service.KeywordExpert
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class KeywordExpertTest {
    private lateinit var expert: ExpertInterface

    @Before
    fun setUp() {
        expert = KeywordExpert()
    }

    @Test
    fun `Should return Adidas based on user description`() = runBlocking {
        val result = expert.generateResponse("White Stripes")
        assertEquals("Adidas", result)
    }

    @Test
    fun `Should return Nike Swoosh based on user description`() = runBlocking {
        val result = expert.generateResponse("curved checkmark")
        assertEquals("Nike Swoosh", result)
    }

    @Test
    fun `Should return Apple based on user description`() = runBlocking {
        val result = expert.generateResponse("Bitten apple logo")
        assertEquals("Apple", result)
    }

    @Test
    fun `Should return Starbucks Siren based on user description`() = runBlocking {
        val result = expert.generateResponse("Green mermaid logo")
        assertEquals("Starbucks Siren", result)
    }

    @Test
    fun `Should return McDonald's Golden Arches based on user description`() = runBlocking {
        val result = expert.generateResponse("Golden arches forming the letter M")
        assertEquals("McDonald's Golden Arches", result)
    }

    @Test
    fun `Should return Olive Branch based on user description`() = runBlocking {
        val result = expert.generateResponse("White dove with olive branch")
        assertEquals("Olive Branch", result)
    }

    @Test
    fun `Should return Twitter based on user description`() = runBlocking {
        val result = expert.generateResponse("Blue bird facing right")
        assertEquals("Twitter Bird", result)
    }

    @Test
    fun `Should return Ankh based on user description`() = runBlocking {
        val result = expert.generateResponse("Ankh-shaped cross with a loop on top")
        assertEquals("Ankh", result)
    }

    @Test
    fun `Should return Ichthys based on user description`() = runBlocking {
        val result = expert.generateResponse("Christian Fish symbol with a tail")
        assertEquals("Ichthys", result)
    }

    @Test
    fun `Should return Crescent and Star based on user description`() = runBlocking {
        val result = expert.generateResponse("Crescent moon with a star")
        assertEquals("Crescent and Star", result)
    }

    @Test
    fun `Should return Dharma Wheel based on user description`() = runBlocking {
        val result = expert.generateResponse("Wheel with eight spokes")
        assertEquals("Dharma Wheel", result)
    }

    @Test
    fun `Should return Cross of Lorraine based on user description`() = runBlocking {
        val result = expert.generateResponse("French Cross with a longer vertical line")
        assertEquals("Cross of Lorraine", result)
    }

    @Test
    fun `Should return Pepsi based on user image`() = runBlocking {
        val result = expert.generateResponse("https://i.imgur.com/6jyhWVN.png", true)
        assertEquals("Pepsi", result)
    }
}