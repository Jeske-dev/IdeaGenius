package de.jeske.restapiwithopenai.modells

import org.bson.types.ObjectId
import java.util.Date

data class Idea(
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val userId: ObjectId,
    val title: String,
    val description: String,
)