package de.jeske.restapiwithopenai.entities

import de.jeske.restapiwithopenai.modells.Request
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("requests")
data class RequestEntity(
    @Id
    val _id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val choice: String,
    val index: Int,
    val date: Date
) {
    constructor(request: Request) : this(request.id, request.processId, request.choice, request.index, request.date)

    fun toRequest() : Request = Request(_id, processId, choice, index, date)
}