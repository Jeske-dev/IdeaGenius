package de.jeske.restapiwithopenai.modells

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

data class Request(
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val choice: String,
    val index: Int,
    val date: Date
)
