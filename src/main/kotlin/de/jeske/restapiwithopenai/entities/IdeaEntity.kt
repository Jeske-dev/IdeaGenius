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
    val userId: ObjectId,
    val title: String,
    val description: String,
    val date: Date,
) {
    constructor(idea: Idea) : this(idea.id, idea.processId, idea.userId, idea.title, idea.description, idea.date)

    fun toIdea() : Idea = Idea(_id, processId, userId, title, description, date)
}