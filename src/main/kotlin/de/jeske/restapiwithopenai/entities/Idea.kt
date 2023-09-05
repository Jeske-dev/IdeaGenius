package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("ideas")
data class Idea(
    @Id
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val userId: ObjectId,
    val responseId: String,
    val title: String,
    val description: String,
    val date: Date
)
