package de.jeske.restapiwithopenai.modells

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

data class Process(
    val id: ObjectId = ObjectId(),
    val userId: ObjectId,
    val lang: String,
    var length: Int,
    val date: Date
)
