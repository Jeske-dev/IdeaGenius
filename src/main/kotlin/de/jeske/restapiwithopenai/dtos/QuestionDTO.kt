package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Question
import org.bson.types.ObjectId
import java.util.*

data class QuestionDTO (
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val requestId: ObjectId,
    val date: Date,
    val question: String,
    val answerChoices: List<String> = emptyList(),
) : Response("question") {
    constructor(question: Question): this(
        question.id,
        question.processId,
        question.requestId,
        question.date,
        question.question,
        question.answerChoices
    )
}
