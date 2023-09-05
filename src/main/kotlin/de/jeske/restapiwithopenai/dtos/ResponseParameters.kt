package de.jeske.restapiwithopenai.dtos

import org.bson.types.ObjectId

data class ResponseParameters(val requestId: ObjectId, val choice: String)
