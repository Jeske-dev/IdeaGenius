package de.jeske.restapiwithopenai.codecs

import de.jeske.restapiwithopenai.entities.IdeaEntity
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import java.util.Date

class IdeaEntityCodec : Codec<IdeaEntity> {
    override fun encode(writer: BsonWriter, value: IdeaEntity, encoderContext: EncoderContext) {
        writer.writeStartDocument()
        writer.writeObjectId("_id", value.id)
        writer.writeObjectId("processId", value.processId)
        writer.writeObjectId("requestId", value.requestId)
        writer.writeDateTime("date", value.date.time)
        writer.writeObjectId("userId", value.userId)
        writer.writeString("title", value.title)
        writer.writeString("description", value.description)
        writer.writeEndDocument()
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): IdeaEntity {
        reader.readStartDocument()
        val id = reader.readObjectId("_id")
        val processId = reader.readObjectId("processId")
        val requestId = reader.readObjectId("requestId")
        val date = Date(reader.readDateTime("date"))
        val userId = reader.readObjectId("userId")
        val title = reader.readString("title")
        val description = reader.readString("description")
        reader.readEndDocument()

        return IdeaEntity(id, processId, requestId, date, userId, title, description)
    }

    override fun getEncoderClass(): Class<IdeaEntity> {
        return IdeaEntity::class.java
    }
}
