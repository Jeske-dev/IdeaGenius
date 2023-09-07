package de.jeske.restapiwithopenai.repositories.codecs

import de.jeske.restapiwithopenai.entities.ProcessEntity
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import java.util.Date

class ProcessEntityCodec : Codec<ProcessEntity> {
    override fun encode(writer: BsonWriter, value: ProcessEntity, encoderContext: EncoderContext) {
        writer.writeStartDocument()
        writer.writeObjectId("_id", value._id)
        writer.writeObjectId("userId", value.userId)
        writer.writeString("lang", value.lang)
        writer.writeDateTime("date", value.date.time)
        writer.writeEndDocument()
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): ProcessEntity {
        reader.readStartDocument()
        val id = reader.readObjectId("_id")
        val userId = reader.readObjectId("userId")
        val lang = reader.readString("lang")
        val date = Date(reader.readDateTime("date"))
        reader.readEndDocument()

        return ProcessEntity(id, userId, lang, date)
    }

    override fun getEncoderClass(): Class<ProcessEntity> {
        return ProcessEntity::class.java
    }
}
