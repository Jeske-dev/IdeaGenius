package de.jeske.restapiwithopenai

import de.jeske.restapiwithopenai.repositories.clients.MongoDBClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MongoDBClientTest {

    @Test
    fun `should return not null if a connection to mongoClient could be established`() {
        assertNotNull(MongoDBClient.getClient())
    }

}