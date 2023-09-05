package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("responses")
data class Response(
    @Id
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val question: String,
    val answerChoices: List<String> = emptyList(),
    val date: Date
)
