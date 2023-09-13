package de.jeske.restapiwithopenai.repositories.mockups

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import de.jeske.restapiwithopenai.modells.TopicChoicePair

object ChatsRepository {

    fun getQuestionChat(pairs: List<TopicChoicePair>): List<ChatMessage> = listOf(
        ChatMessage(
            role = ChatRole.System,
            content = """
                You will assist me in discovering my next project idea by crafting targeted questions based on user input.
        
                Your task is to create a JSON object with the following keys:
        
                question (A concise and creative question, avoiding yes/no queries and rooted in user input)
                answer_choices (A list of recommended answers to the question, never empty)
                question_topic (A single descriptive word that captures the question's topic and answer choices)
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.Assistant,
            content = """
                {
                    "question": "What kind of project do you want to tackle next?",
                    "answer_choices": [
                        "Coding Project",
                        "Wood processing Project",
                        "Social Project",
                        "Musical Project"
                    ],
                    "question_topic": "Project kind"
                }
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.User,
            content = """
                [
                    "Project kind": "Coding Project",
                    ${pairsToString(pairs)}
                ]
                Based on this user choices, go on with the next question. ONLY write the json object.
            """.trimIndent()
        )
    )

    fun getIdeaChat(pairs: List<TopicChoicePair>): List<ChatMessage> = listOf(
        ChatMessage(
            role = ChatRole.System,
            content = """
                You will assist me in discovering my next project idea by composing the given data to a idea.

                Your task is to create a JSON object with the following keys:

                title (A concise and creative title to describe my next coding project, rooted in user input)
                description (A concise description of my next coding project, describe the topic, list frameworks, technologies and languages)
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.User,
            content = """
                Ignore content of previous messages!
                [
                    "Project kind": "Coding Project",
                ]
                Based on this user choices create a idea. ONLY write the json object.
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.Assistant,
            content = """
                {
                  "title": "CodeHub: Collaborative Coding Platform",
                  "description": "Create a web-based collaborative coding platform using modern technologies like React, Node.js, and WebSocket. This platform will allow multiple users to collaborate on coding projects in real-time, providing features such as code editing, real-time updates, chat, and version control. It will be a versatile tool for remote teams, coding bootcamps, and open-source contributors."
                }
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.User,
            content = """
                Ignore content of previous messages!
                [
                    ${pairsToString(pairs)}
                ]
                Based on this user choices create a idea. ONLY write the json object.
            """.trimIndent()
        )
    )

    private fun pairsToString(pairs: List<TopicChoicePair>) : String {
        var s = ""
        pairs.forEach {
            s += it.toString()
        }
        return s
    }

}