package de.jeske.restapiwithopenai.dtos

import org.bson.types.ObjectId

data class ProcessStartParametersDTO(val userId: ObjectId, val lang: String)
