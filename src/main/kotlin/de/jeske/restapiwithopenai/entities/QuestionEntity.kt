package de.jeske.restapiwithopenai.entities

import de.jeske.restapiwithopenai.modells.Question
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("questions")
data class QuestionEntity (
    @Id
    val _id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val question: String,
    val answerChoices: List<String> = emptyList(),
) {
    constructor(question: Question) : this(question.id, question.processId, question.requestId, question.date, question.question, question.answerChoices)

    fun toQuestion() : Question = Question(_id, processId, requestId, date, question, answerChoices)
}