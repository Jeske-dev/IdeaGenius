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
    val question: String,
    val questionTopic: String,
    val answerChoices: List<String> = emptyList(),
    val index: Int,
    val date: Date,
) {
    constructor(question: Question) : this(question.id, question.processId, question.question, question.questionTopic, question.answerChoices, question.index, question.date, )

    fun toQuestion() : Question = Question(_id, processId, question, questionTopic, answerChoices, index, date, )
}