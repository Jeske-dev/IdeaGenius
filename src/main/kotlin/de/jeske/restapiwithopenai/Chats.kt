package de.jeske.restapiwithopenai

import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import de.jeske.restapiwithopenai.modells.TopicChoicePair

object Chats {

    fun generateGetQuestionChat(pairs: List<TopicChoicePair>): List<ChatMessage> = listOf(
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

    private fun pairsToString(pairs: List<TopicChoicePair>) : String {
        var s = ""
        pairs.forEach {
            s += "\"${it.topic}\": \"${it.choice}\","
        }
        return s
    }

}