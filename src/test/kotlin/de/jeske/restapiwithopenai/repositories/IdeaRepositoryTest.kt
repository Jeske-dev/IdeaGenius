package de.jeske.restapiwithopenai.repositories

import IdeaRepository
import de.jeske.restapiwithopenai.modells.Idea
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date

class IdeaRepositoryTest {

    private val ideaRepository = IdeaRepository(MongoDBClient)

    @Test
    fun getIdeaById() {
        val testId = ObjectId("64f9c54ae038a857aab806f0")
        val idea = ideaRepository.getIdeaById(testId)
        val expectedTitle = "RESTful API with ChatGPT"
        assertNotNull(idea)
        assertEquals(expectedTitle, idea?.title)
    }

    @Test
    fun getIdeaByProcessId() {
        val testProcessId = ObjectId("64f9a983e4d9842700e875dd")
        val ideas = ideaRepository.getIdeaByProcessId(testProcessId)
        assertNotNull(ideas)
    }

    @Test
    fun getAllIdeasByUserId() {
        val testUserId = ObjectId("64f5ce6e7b4d45c7c950c2e3")
        val ideas = ideaRepository.getAllIdeasByUserId(testUserId)
        assertNotNull(ideas)
    }

    @Test
    fun createIdea() {
        val idea = Idea(
            ObjectId(),
            ObjectId("64f9a983e4d9842700e875dd"),
            ObjectId("64f9bf4d3ab56457e7dd56d7"),
            Date.from(Instant.now()),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "RESTful API with ChatGPT",
            "Develop a RESTful API with Spring in Kotlin. Use MongoDB as database. For data processing you can use ChatGPT API. Have fun!"
        )
        val acknowledged = ideaRepository.createIdea(idea)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteIdea() {
        val idea = Idea(
            ObjectId(),
            ObjectId("64f9a983e4d9842700e875dd"),
            ObjectId("64f9bf4d3ab56457e7dd56d7"),
            Date.from(Instant.now()),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "Test Idea",
            "descriptiondescriptiondescription"
        )
        val acknowledged = ideaRepository.createIdea(idea)
        assert(acknowledged)
        if (acknowledged) {
            val acknowledged2 = ideaRepository.deleteIdea(idea.id)
            assert(acknowledged2)
        }
    }

    @Test
    fun updateIdea() {
        val idea = Idea(
            ObjectId(),
            ObjectId("64f9a983e4d9842700e875dd"),
            ObjectId("64f9bf4d3ab56457e7dd56d7"),
            Date.from(Instant.now()),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "RESTful API with ChatGPT",
            "Develop a RESTful API with Spring in Kotlin. Use MongoDB as database. For data processing you can use ChatGPT API. Have a lot of fun!"
        )
        val acknowledge = ideaRepository.updateIdea(idea)
        assert(acknowledge)
    }

}
