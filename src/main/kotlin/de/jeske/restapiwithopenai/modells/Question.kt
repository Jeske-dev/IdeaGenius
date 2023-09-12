package de.jeske.restapiwithopenai.modells

import org.bson.types.ObjectId
import java.util.*

data class Question (
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val question: String,
    val questionTopic: String,
    val answerChoices: List<String> = emptyList(),
    val index: Int,
    val date: Date,
) : Response()
