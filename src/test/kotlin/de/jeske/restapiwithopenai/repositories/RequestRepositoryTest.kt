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
        val testId = ObjectId("64fb1238f885080c0e8d563e")
        val request = requestRepository.getRequestById(testId)
        val expectedIndex = 1
        assertNotNull(request)
        assertEquals(expectedIndex, request?.index)
    }

    @Test
    fun getAllRequestsByProcessId() {
        val testProcessId = ObjectId("64fb0e84e6d1820aa7f3419d")
        val requests = requestRepository.getAllRequestsByProcessId(testProcessId)
        val count = requests?.size
        println("Requests Count: $count")
        assertNotNull(requests)
    }

    @Test
    fun createRequest() {
        val request = Request(
            ObjectId(),
            ObjectId("64fb1238f885080c0e8d563e"),
            "Both",
            3,
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
            "first",
            2,
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
            ObjectId("64fb1238f885080c0e8d563e"),
            ObjectId("64fb0e84e6d1820aa7f3419d"),
            "Other",
            1,
            Date.from(Instant.now())
        )
        val acknowledge = requestRepository.updateRequest(request)
        assert(acknowledge)
    }

}
