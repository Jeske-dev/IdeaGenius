package de.jeske.restapiwithopenai

import de.jeske.restapiwithopenai.repositories.MongoDBClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MongoDBClientTest {

    @Test
    fun `should return not null if a connection to mongoClient could be established`() {
        assertNotNull(MongoDBClient.getClient())
    }

    @Test
    fun `should return the name Hannes of a test user`() {
        assertNotNull(MongoDBClient.getRandomTestUser())
    }

    @Test
    fun `should return true if object could be added`() {
        val expected = true
        assertEquals(expected, MongoDBClient.insertTestUser())
    }

}