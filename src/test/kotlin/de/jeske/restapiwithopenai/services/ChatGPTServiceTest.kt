package de.jeske.restapiwithopenai.services

import de.jeske.restapiwithopenai.dtos.IdeaChatGPTDTO
import de.jeske.restapiwithopenai.dtos.QuestionChatGPTDTO
import de.jeske.restapiwithopenai.modells.Question
import de.jeske.restapiwithopenai.modells.Request
import de.jeske.restapiwithopenai.modells.TopicChoicePair
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import java.util.*

class ChatGPTServiceTest {

    private val chatGPTService = ChatGPTService()

    @Test
    fun pair() {
        val questions = listOf(
            Question(
                ObjectId(),
                ObjectId(),
                "How are you today?",
                "mood",
                listOf("Good", "Bad",),
                0,
                Date.from(Instant.now())
            ),
            Question(
                ObjectId(),
                ObjectId(),
                "Why are you at Staffbase?",
                "internship reason",
                listOf("learning new things", "meeting new people",),
                2,
                Date.from(Instant.now())
            ),
        )
        val requests = listOf(
            Request(
                ObjectId(),
                ObjectId(),
                "Good",
                1,
                Date.from(Instant.now())
            ),
            Request(
                ObjectId(),
                ObjectId(),
                "learning new things",
                3,
                Date.from(Instant.now())
            ),
        )

        val expected = listOf(
            TopicChoicePair("mood", "Good"),
            TopicChoicePair("internship reason", "learning new things"),
        )

        assertEquals(expected.toString(), chatGPTService.pair(questions, requests).toString())
    }

    @Test
    fun parseQuestion() {
        val raw = """
            {
            "question": "What specific area of coding are you interested in?",
            "answer_choices": [
                "Web Development",
                "Mobile App Development",
                "Data Science",
                "Machine Learning"
            ],
            "question_topic": "Coding Project Area"
        }
        """.trimIndent()
        val expected = QuestionChatGPTDTO(
            question = "What specific area of coding are you interested in?",
            answerChoices = listOf(
                "Web Development",
                "Mobile App Development",
                "Data Science",
                "Machine Learning"
            ),
            questionTopic = "Coding Project Area"
        )
        assertEquals(expected, chatGPTService.parseStringToQuestionChatGPTDTO(raw))
    }

    @Test
    fun parseIdea() {
        val raw = """
            {
              "title": "CodeHub: Collaborative Coding Platform",
              "description": "Create a web-based collaborative coding platform using modern technologies like React, Node.js, and WebSocket. This platform will allow multiple users to collaborate on coding projects in real-time, providing features such as code editing, real-time updates, chat, and version control. It will be a versatile tool for remote teams, coding bootcamps, and open-source contributors."
            }
        """.trimIndent()
        val expected = IdeaChatGPTDTO(
            title = "CodeHub: Collaborative Coding Platform",
            description = "Create a web-based collaborative coding platform using modern technologies like React, Node.js, and WebSocket. This platform will allow multiple users to collaborate on coding projects in real-time, providing features such as code editing, real-time updates, chat, and version control. It will be a versatile tool for remote teams, coding bootcamps, and open-source contributors."
        )
        assertEquals(expected, chatGPTService.parseStringToIdeaChatGPTDTO(raw))
    }
}