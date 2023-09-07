package de.jeske.restapiwithopenai.repositories.codecs

import de.jeske.restapiwithopenai.entities.QuestionEntity
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import java.util.Date

class QuestionEntityCodec : Codec<QuestionEntity> {
    override fun encode(writer: BsonWriter, value: QuestionEntity, encoderContext: EncoderContext) {
        writer.writeStartDocument()
        writer.writeObjectId("_id", value._id)
        writer.writeObjectId("processId", value.processId)
        writer.writeObjectId("requestId", value.requestId)
        writer.writeDateTime("date", value.date.time)
        writer.writeString("question", value.question)
        writer.writeStartArray("answerChoices")
        value.answerChoices.forEach { choice -> writer.writeString(choice) }
        writer.writeEndArray()
        writer.writeEndDocument()
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): QuestionEntity {
        reader.readStartDocument()
        val id = reader.readObjectId("_id")
        val processId = reader.readObjectId("processId")
        val requestId = reader.readObjectId("requestId")
        val date = Date(reader.readDateTime("date"))
        val question = reader.readString("question")
        val answerChoices = mutableListOf<String>()
        reader.readStartArray()
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            answerChoices.add(reader.readString())
        }
        reader.readEndArray()
        reader.readEndDocument()

        return QuestionEntity(id, processId, requestId, date, question, answerChoices)
    }

    override fun getEncoderClass(): Class<QuestionEntity> {
        return QuestionEntity::class.java
    }
}
