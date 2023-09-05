package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.*

class Question (
    @Id
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val question: String,
    val answerChoices: List<String> = emptyList(),
) : Response (
    _id = id,
    _processId = processId,
    _requestId = requestId,
    _date = date
)
