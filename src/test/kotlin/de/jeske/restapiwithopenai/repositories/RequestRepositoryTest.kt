package de.jeske.restapiwithopenai.repositories

import RequestRepository
import de.jeske.restapiwithopenai.modells.Request
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date

class RequestRepositoryTest {

    private val requestRepository = RequestRepository(MongoDBClient)

    @Test
    fun getRequestById() {
        val testId = ObjectId("64f9bf4d3ab56457e7dd56d7")
        val request = requestRepository.getRequestById(testId)
        val expectedChoice = "1"
        assertNotNull(request)
        assertEquals(expectedChoice, request?.choice)
    }

    @Test
    fun getAllRequestsByProcessId() {
        val testProcessId = ObjectId("64f9a983e4d9842700e875dd")
        val requests = requestRepository.getAllRequestsByProcessId(testProcessId)
        assertNotNull(requests)
    }

    @Test
    fun createRequest() {
        val request = Request(
            ObjectId(),
            ObjectId("64f9a983e4d9842700e875dd"),
            null,
            null,
            Date.from(Instant.now())
        )
        val acknowledged = requestRepository.createRequest(request)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteRequest() {
        val request = Request(
            ObjectId(),
            ObjectId("64f9a983e4d9842700e875dd"),
            null,
            null,
            Date.from(Instant.now())
        )
        val acknowledged = requestRepository.createRequest(request)
        assert(acknowledged)
        if (acknowledged) {
            val acknowledged2 = requestRepository.deleteRequest(request.id)
            assert(acknowledged2)
        }
    }

    @Test
    fun updateRequest() {
        val request = Request(
            ObjectId("64f9bf4d3ab56457e7dd56d7"),
            ObjectId("64f9a983e4d9842700e875dd"),
            null,
            "1",
            Date.from(Instant.now())
        )
        val acknowledge = requestRepository.updateRequest(request)
        assert(acknowledge)
    }

}
