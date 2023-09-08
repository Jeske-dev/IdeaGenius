package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Idea
import org.bson.types.ObjectId
import java.util.Date

data class IdeaDTO(
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val userId: ObjectId,
    val title: String,
    val description: String,
    val date: Date,
) : Response("idea") {
    constructor(idea: Idea): this(
        idea.id,
        idea.processId,
        idea.userId,
        idea.title,
        idea.description,
        idea.date,
    )
}