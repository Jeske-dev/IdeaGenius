package de.jeske.restapiwithopenai.entities

import de.jeske.restapiwithopenai.modells.Idea
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("ideas")
data class IdeaEntity(
    @Id
    val _id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val userId: ObjectId,
    val title: String,
    val description: String,
) {
    constructor(idea: Idea) : this(idea.id, idea.processId, idea.requestId, idea.date, idea.userId, idea.title, idea.description)

    fun toIdea() : Idea = Idea(_id, processId, requestId, date, userId, title, description)
}