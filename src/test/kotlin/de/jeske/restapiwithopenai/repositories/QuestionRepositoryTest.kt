package de.jeske.restapiwithopenai.repositories

import de.jeske.restapiwithopenai.modells.Question
import de.jeske.restapiwithopenai.repositories.clients.MongoDBClient
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date

class QuestionRepositoryTest {

    private val questionRepository = QuestionRepository(MongoDBClient)

    @Test
    fun getQuestionById() {
        val testId = ObjectId("64fb11299c61af5b92c142da")
        val question = questionRepository.getQuestionById(testId)
        val expectedProcessId = ObjectId("64fb0e84e6d1820aa7f3419d")
        assertNotNull(question)
        assertEquals(expectedProcessId, question?.processId)
    }

    @Test
    fun getAllQuestionsByProcessId() {
        val testProcessId = ObjectId("64fb0e84e6d1820aa7f3419d")
        val questions = questionRepository.getAllQuestionsByProcessId(testProcessId)
        val count = questions?.size
        println("Questions Count: $count")
        assertNotNull(questions)
    }

    @Test
    fun createQuestion() {
        val question = Question(
            ObjectId(),
            ObjectId("64fb0e84e6d1820aa7f3419d"), // id of process de
            "What do you prefer?",
            "Coding Area",
            listOf("Frontend", "Backend", "Both"),
            2,
            Date.from(Instant.now()),
        )
        val acknowledged = questionRepository.createQuestion(question)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteQuestion() {
        val question = Question(
            ObjectId(),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "Test Question",
            "Question Area",
            listOf("Choice 1", "Choice 2", "Choice 3"),
            1,
            Date.from(Instant.now()),
        )
        val acknowledged = questionRepository.createQuestion(question)
        assert(acknowledged)
        if (acknowledged) {
            val acknowledged2 = questionRepository.deleteQuestion(question.id)
            assert(acknowledged2)
        }
    }

    @Test
    fun updateQuestion() {
        val question = Question(
            ObjectId("64fb11299c61af5b92c142da"),
            ObjectId("64fb0e84e6d1820aa7f3419d"),
            "What is your favourite programming language?",
            "Programming Language",
            listOf("Java", "JavaScript", "Python", "C", "Other"),
            0,
            Date.from(Instant.now()),
        )
        val acknowledged = questionRepository.updateQuestion(question)
        assert(acknowledged)
    }

}
