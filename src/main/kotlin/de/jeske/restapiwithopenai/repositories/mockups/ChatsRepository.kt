package de.jeske.restapiwithopenai.repositories.mockups

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import de.jeske.restapiwithopenai.modells.QuestionChoicePair

object ChatsRepository {

    fun getQuestionChat(pairs: List<QuestionChoicePair>, lang: String): List<ChatMessage> = listOf(
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
            role = ChatRole.User,
            content = """
                Please Ignore all previous messages!
                [
                    "Which kind of project do you like to start?": "Coding Project",
                    "What specific area of coding are you interested in for your project?": "Web Development",
                    "How much experience do you already have in this field": "none",
                    "How long should the project take?": "2 weeks",
                    "What specific features or functionality would you like to develop for your website?": "Online booking system"
                ]
                Based on this user choices, go on with the next question. ONLY write the json object.
                
                Please answer in following language: en
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.Assistant,
            content = """
                {
                    "question": "What kind of booking system do you want to develop?",
                    "answer_choices": [
                        "car rental",
                        "vacation booking",
                        "experience booking"
                    ],
                    "question_topic": "kind of booking system"
                }
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.User,
            content = """
                Please ignore all previous messages!
                [
                    "Which kind of project do you like to start?": "Coding Project",
                ]
                Based on this user choices, go on with the next question. ONLY write the json object.
                
                Please answer in following language: $lang
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.Assistant,
            content = """
                {
                    "question": "Which of the topics are you interested in and could you imagine doing a project on?",
                    "answer_choices": [
                        "stones",
                        "time management",
                        "vacation",
                        "social impact",
                    ],
                    "question_topic": "kind of booking system"
                }
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.User,
            content = """
                Please ignore all previous messages!
                [
                    "Project kind": "Coding Project",
                    ${pairsToString(pairs)}
                ]
                Based on this user choices, go on with the next question. ONLY write the json object.
                
                Please answer in following language: $lang
            """.trimIndent()
        )
    )

    fun getIdeaChat(pairs: List<QuestionChoicePair>, lang: String): List<ChatMessage> = listOf(
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
                    "Which kind of project do you like to start?": "Coding Project",
                    "What specific area of coding are you interested in for your project?": "Web Development",
                    "How much experience do you already have in this field": "none",
                    "How long should the project take?": "2 weeks",
                    "What specific features or functionality would you like to develop for your website?": "Online booking system"
                ]
                Based on this user choices create a idea. ONLY write the json object.
                
                Please answer in following language: de
            """.trimIndent()
        ),
        ChatMessage(
            role = ChatRole.Assistant,
            content = """
                {
                  "title": "Buchungsplattform für Online-Terminbuchungen",
                  "description": "Entwickle eine Webanwendung für Online-Terminbuchungen, die es Unternehmen ermöglicht, ihre Dienstleistungen online anzubieten. Verwende moderne Webtechnologien wie React, Node.js und MongoDB. Achte auf eine benutzerfreundliche Oberfläche! Sei creativ und tobe dich aus!"
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
                
                Please answer in following language: $lang
            """.trimIndent()
        )
    )

    private fun pairsToString(pairs: List<QuestionChoicePair>) : String {
        var s = ""
        pairs.forEach {
            s += it.toString()
        }
        return s
    }

}