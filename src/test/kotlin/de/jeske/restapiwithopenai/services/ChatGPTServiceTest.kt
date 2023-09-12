package de.jeske.restapiwithopenai.services

import de.jeske.restapiwithopenai.dtos.IdeaChatGPTDTO
import de.jeske.restapiwithopenai.dtos.QuestionChatGPTDTO
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ChatGPTServiceTest {

    private val chatGPTService = ChatGPTService()

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