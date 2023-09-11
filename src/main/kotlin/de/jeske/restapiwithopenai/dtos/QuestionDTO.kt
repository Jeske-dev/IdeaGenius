package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Question
import org.bson.types.ObjectId
import java.util.*

data class QuestionDTO (
    val id: String,
    val processId: String,
    val question: String,
    val answerChoices: List<String> = emptyList(),
    val index: Int,
    val date: Date,
) : ResponseDTO("question") {
    constructor(question: Question): this(
        question.id.toHexString(),
        question.processId.toHexString(),
        question.question,
        question.answerChoices,
        question.index,
        question.date,
    )
}
