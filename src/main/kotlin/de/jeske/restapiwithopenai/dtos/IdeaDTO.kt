package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Idea
import org.bson.types.ObjectId
import java.util.Date

data class IdeaDTO(
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val userId: ObjectId,
    val title: String,
    val description: String,
) : Response("idea") {
    constructor(idea: Idea): this(
        idea.id,
        idea.processId,
        idea.requestId,
        idea.date,
        idea.userId,
        idea.title,
        idea.description
    )
}