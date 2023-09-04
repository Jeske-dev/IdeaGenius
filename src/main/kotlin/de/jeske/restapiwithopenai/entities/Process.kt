package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("processes")
data class Process(
    @Id
    val id: ObjectId = ObjectId(),
    @Id
    val userId: ObjectId,
    val lang: String,
    val date: Date
)
