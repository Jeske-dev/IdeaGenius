package de.jeske.restapiwithopenai.dtos
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionChatGPTDTO @JsonCreator constructor(
    @JsonProperty("question") val question: String,
    @JsonProperty("answer_choices") val answerChoices: List<String>,
    @JsonProperty("question_topic") val questionTopic: String
)