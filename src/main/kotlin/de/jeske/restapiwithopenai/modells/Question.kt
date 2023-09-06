package de.jeske.restapiwithopenai.modells

import org.bson.types.ObjectId
import java.util.*

data class Question (
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val question: String,
    val answerChoices: List<String> = emptyList(),
)
