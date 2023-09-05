package de.jeske.restapiwithopenai.dtos

import org.bson.types.ObjectId

data class ProcessStartParameters(val userId: ObjectId, val lang: String)
