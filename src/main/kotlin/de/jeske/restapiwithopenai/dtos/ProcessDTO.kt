package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.modells.Process
import org.bson.types.ObjectId
import java.util.Date

data class ProcessDTO(
    val id: String,
    val userId: String,
    val lang: String,
    val date: Date
) {
    constructor(process: Process): this(
        process.id.toHexString(),
        process.userId.toHexString(),
        process.lang,
        process.date
    )
}