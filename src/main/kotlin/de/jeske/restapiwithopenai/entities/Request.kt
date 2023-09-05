package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("requests")
data class Request(
    @Id
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val responseId: ObjectId?,
    val choice: String?,
    val date: Date
)
