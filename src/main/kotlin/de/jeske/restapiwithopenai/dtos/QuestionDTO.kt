package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Question
import org.bson.types.ObjectId
import java.util.*

data class QuestionDTO (
    val id: ObjectId = ObjectId(),
    val processId: ObjectId,
    val question: String,
    val answerChoices: List<String> = emptyList(),
    val index: Int,
    val date: Date,
) : Response("question") {
    constructor(question: Question): this(
        question.id,
        question.processId,
        question.question,
        question.answerChoices,
        question.index,
        question.date,
    )
}
