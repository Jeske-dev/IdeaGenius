package de.jeske.restapiwithopenai.repositories

import de.jeske.restapiwithopenai.modells.Process
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date

class ProcessRepositoryTest {

    private val processRepository = ProcessRepository(MongoDBClient)

    @Test
    fun getProcessById() {
        val testId = ObjectId("64f9a983e4d9842700e875dd")
        val process = processRepository.getProcessById(testId)
        val expectedLang = "en"
        assertNotNull(process)
        assertEquals(expectedLang, process?.lang)
    }

    @Test
    fun getAllProcessWithUserId() {
        val testUserId = ObjectId("64f5ce6e7b4d45c7c950c2e3")
        val processes = processRepository.getAllProcessWithUserId(testUserId)
        assertNotNull(processes)
    }

    @Test
    fun createAndDeleteProcess() {
        val process = Process(
            ObjectId(),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "de",
            Date.from(Instant.now())
        )
        val acknowledged = processRepository.createProcess(process)
        assert(acknowledged)
        if (acknowledged) {
            val acknowledged2 = processRepository.deleteProcess(process.id)
            assert(acknowledged2)
        }
    }

    @Test
    fun updateProcess() {
        val process = Process(
            ObjectId("64f9a983e4d9842700e875dd"),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "en",
            Date.from(Instant.parse("2023-09-07T10:44:19.605+00:00"))
        )
        val acknowledge = processRepository.updateProcess(process)
        assert(acknowledge)
    }

}