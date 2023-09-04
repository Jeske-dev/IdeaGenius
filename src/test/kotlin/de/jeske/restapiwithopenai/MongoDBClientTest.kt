package de.jeske.restapiwithopenai

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MongoDBClientTest {

    @Test
    fun `should return not null if a connection to mongoClient could be established`() {
        assertNotNull(MongoDBClient.init())
    }

    @Test
    fun `should return the name Hannes of a test user`() {
        val expected = "Hannes"
        assertEquals(expected, MongoDBClient.getTestUser()?.firstname)
    }

}