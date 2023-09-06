package de.jeske.restapiwithopenai.modells

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class User(
    val id: ObjectId = ObjectId(),
    val email: String,
    val surname: String,
    val firstname:String,
    val processIds: List<String> = emptyList()
)