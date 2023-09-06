package de.jeske.restapiwithopenai.dtos

import org.bson.types.ObjectId

data class RequestParametersDTO(val requestId: ObjectId, val choice: String)
