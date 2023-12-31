package de.jeske.restapiwithopenai.entities

import de.jeske.restapiwithopenai.modells.Process
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document("processes")
data class ProcessEntity(
    @Id
    val _id: ObjectId = ObjectId(),
    val userId: ObjectId,
    val lang: String,
    val length: Int,
    val date: Date
) {
    constructor(process: Process) : this(process.id, process.userId, process.lang, process.length, process.date)
    fun toProcess() : Process = Process(_id, userId, lang, length, date)
}
