package de.jeske.restapiwithopenai.repositories

import QuestionRepository
import de.jeske.restapiwithopenai.modells.Question
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.Date

class QuestionRepositoryTest {

    private val questionRepository = QuestionRepository(MongoDBClient)

    @Test
    fun getQuestionById() {
        val testId = ObjectId("64f9c31ee4f69c3162ac2d59")
        val question = questionRepository.getQuestionById(testId)
        val expectedProcessId = ObjectId("64f9a983e4d9842700e875dd")
        assertNotNull(question)
        assertEquals(expectedProcessId, question?.processId)
    }

    @Test
    fun getAllQuestionsByProcessId() {
        val testProcessId = ObjectId("64f9a983e4d9842700e875dd")
        val questions = questionRepository.getAllQuestionsByProcessId(testProcessId)
        assertNotNull(questions)
    }

    @Test
    fun createQuestion() {
        val question = Question(
            ObjectId(),
            ObjectId("64f9a983e4d9842700e875dd"),
            ObjectId("64f9bf4d3ab56457e7dd56d7"),
            Date.from(Instant.now()),
            "What is your favourite programming language",
            listOf("Java", "JavaScript", "Python", "C", "Other")
        )
        val acknowledged = questionRepository.createQuestion(question)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteQuestion() {
        val question = Question(
            ObjectId(),
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            ObjectId("64f9a983e4d9842700e875dd"),
            Date.from(Instant.now()),
            "Test Question",
            listOf("Choice 1", "Choice 2", "Choice 3")
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
            ObjectId("64f9c31ee4f69c3162ac2d59"),
            ObjectId("64f9a983e4d9842700e875dd"),
            ObjectId("64f9bf4d3ab56457e7dd56d7"),
            Date.from(Instant.now()),
            "What is your favourite programming language?",
            listOf("Java", "JavaScript", "Python", "C", "Other")
        )
        val acknowledged = questionRepository.updateQuestion(question)
        assert(acknowledged)
    }

}
