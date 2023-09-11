package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Idea
import org.bson.types.ObjectId
import java.util.Date

data class IdeaDTO(
    val id: String,
    val processId: String,
    val userId: String,
    val title: String,
    val description: String,
    val date: Date,
) : ResponseDTO("idea") {
    constructor(idea: Idea): this(
        idea.id.toHexString(),
        idea.processId.toHexString(),
        idea.userId.toHexString(),
        idea.title,
        idea.description,
        idea.date,
    )
}