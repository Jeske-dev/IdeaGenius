package de.jeske.restapiwithopenai.dtos
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class IdeaChatGPTDTO @JsonCreator constructor(
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String
)