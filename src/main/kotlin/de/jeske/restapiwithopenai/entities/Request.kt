package de.jeske.restapiwithopenai.entities

import java.util.Date

data class Request(val _id: String, val processId: String?, val responseId: String?, val choice: String?, val date: Date)
