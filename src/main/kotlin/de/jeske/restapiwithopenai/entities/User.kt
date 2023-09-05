package de.jeske.restapiwithopenai.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
//@Serializable
data class User(
    @Id
    //@SerialName("_id")
    //@Contextual
    val id: ObjectId,
    val email: String,
    val surname: String,
    val firstname:String,
    val processIds: List<String> = emptyList()
)
