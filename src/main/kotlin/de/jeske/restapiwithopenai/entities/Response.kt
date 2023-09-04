package de.jeske.restapiwithopenai.entities

import java.util.Date

data class Response(val _id: String, val processId: String, val requestId: String, val question: String, val answerChoices: List<String>, val date: Date)
