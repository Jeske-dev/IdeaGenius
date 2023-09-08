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
        val testId = ObjectId("64fb0e84e6d1820aa7f3419d")
        val process = processRepository.getProcessById(testId)
        val expectedLang = "de"
        assertNotNull(process)
        assertEquals(expectedLang, process?.lang)
    }

    @Test
    fun getAllProcessWithUserId() {
        val testUserId = ObjectId("64fb0d02c9d972019fa88eb6")
        val processes = processRepository.getAllProcessWithUserId(testUserId)
        val count = processes?.size
        println("Processes Count: $count")
        assertNotNull(processes)
    }

    @Test
    fun createProcess() {
        val process = Process(
            ObjectId(),
            ObjectId("64fb0d02c9d972019fa88eb6"), // id of userOne@tester.de
            "en",
            date = Date.from(Instant.now())
        )
        val acknowledged = processRepository.createProcess(process)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteProcess() {
        val process = Process(
            ObjectId(),
            ObjectId(), // just random id
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
            ObjectId("64fb0f032d224e7249d20abe"),
            ObjectId("64fb0d02c9d972019fa88eb6"), // id of userOne@tester.de
            "en",
            Date.from(Instant.now())
        )
        val acknowledge = processRepository.updateProcess(process)
        assert(acknowledge)
    }

}