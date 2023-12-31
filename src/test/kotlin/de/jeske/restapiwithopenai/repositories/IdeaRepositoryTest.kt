package de.jeske.restapiwithopenai.repositories

import de.jeske.restapiwithopenai.modells.Idea
import de.jeske.restapiwithopenai.repositories.clients.MongoDBClient
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date

class IdeaRepositoryTest {

    private val ideaRepository = IdeaRepository(MongoDBClient)

    @Test
    fun getIdeaById() {
        val testId = ObjectId("64fb1354c019f422a3cc9575")
        val idea = ideaRepository.getIdeaById(testId)
        val expectedTitle = "RESTful API with ChatGPT"
        assertNotNull(idea)
        assertEquals(expectedTitle, idea?.title)
    }

    @Test
    fun getIdeaByProcessId() {
        val testProcessId = ObjectId("64fb0e84e6d1820aa7f3419d")
        val ideas = ideaRepository.getIdeaByProcessId(testProcessId)
        assertNotNull(ideas)
    }

    @Test
    fun getAllIdeasByUserId() {
        val testUserId = ObjectId("64fb0d02c9d972019fa88eb6")
        val ideas = ideaRepository.getAllIdeasByUserId(testUserId)
        val count = ideas?.size
        println("Ideas Count: $count")
        assertNotNull(ideas)
    }

    @Test
    fun createIdea() {
        val idea = Idea(
            ObjectId(),
            ObjectId("64fb0e84e6d1820aa7f3419d"),
            ObjectId("64fb0d02c9d972019fa88eb6"),
            "Simple Website",
            "Create a simple portfolio website with HTML, CSS and JavaScript!",
            Date.from(Instant.now()),
        )
        val acknowledged = ideaRepository.createIdea(idea)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteIdea() {
        val idea = Idea(
            ObjectId(),
            ObjectId("64fb0e84e6d1820aa7f3419d"),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "Test Idea",
            "descriptiondescriptiondescription",
            Date.from(Instant.now()),
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
            ObjectId("64fb1354c019f422a3cc9575"),
            ObjectId("64fb0e84e6d1820aa7f3419d"),
            ObjectId("64fb0d02c9d972019fa88eb6"),
            "RESTful API with ChatGPT",
            "Develop a RESTful API with Spring in Kotlin. Use MongoDB as database. For data processing you can use ChatGPT API. Have a lot of fun!",
            Date.from(Instant.now()),
        )
        val acknowledge = ideaRepository.updateIdea(idea)
        assert(acknowledge)
    }

}
