package de.jeske.restapiwithopenai.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("responses")
open class Response(
    @Id
    val _id: ObjectId = ObjectId(),
    val _processId: ObjectId,
    val _requestId: ObjectId,
    val _date: Date
)
