package de.jeske.restapiwithopenai.entities

import java.util.Date

data class Idea(val _id: String, val processId: String, val userId: String, val responseId: String, val title: String, val description: String, val date: Date)
