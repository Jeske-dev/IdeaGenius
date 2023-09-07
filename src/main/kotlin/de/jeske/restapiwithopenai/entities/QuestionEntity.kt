package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("questions")
data class QuestionEntity (
    @Id
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val question: String,
    val answerChoices: List<String> = emptyList(),
)
