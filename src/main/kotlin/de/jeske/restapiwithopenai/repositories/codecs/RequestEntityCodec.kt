package de.jeske.restapiwithopenai.repositories.codecs

import de.jeske.restapiwithopenai.entities.RequestEntity
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.types.ObjectId
import java.util.Date

class RequestEntityCodec : Codec<RequestEntity> {
    override fun encode(writer: BsonWriter, value: RequestEntity, encoderContext: EncoderContext) {
        writer.writeStartDocument()
        writer.writeObjectId("_id", value._id)
        writer.writeObjectId("processId", value.processId)
        if (value.responseId != null) {
            writer.writeObjectId("responseId", value.responseId)
        } else {
            writer.writeNull("responseId")
        }
        if (value.choice != null) {
            writer.writeString("choice", value.choice)
        } else {
            writer.writeNull("choice")
        }
        writer.writeDateTime("date", value.date.time)
        writer.writeEndDocument()
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): RequestEntity {
        reader.readStartDocument()
        val id = reader.readObjectId("_id")
        val processId = reader.readObjectId("processId")
        val responseId = reader.readNullableObjectId("responseId")
        val choice = reader.readNullableString("choice")
        val date = Date(reader.readDateTime("date"))
        reader.readEndDocument()

        return RequestEntity(id, processId, responseId, choice, date)
    }

    override fun getEncoderClass(): Class<RequestEntity> {
        return RequestEntity::class.java
    }
}

// Extension functions for reading nullable values
private fun BsonReader.readNullableObjectId(name: String): ObjectId? {
    if (this.readBsonType() == BsonType.NULL) {
        this.readNull()
        return null
    }
    return this.readObjectId(name)
}

private fun BsonReader.readNullableString(name: String): String? {
    if (this.readBsonType() == BsonType.NULL) {
        this.readNull()
        return null
    }
    return this.readString(name)
}