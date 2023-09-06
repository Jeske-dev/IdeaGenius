package de.jeske.restapiwithopenai.codecs

import de.jeske.restapiwithopenai.entities.User
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

// TODO: change User to UserEntity
class UserEntityCodec : Codec<User> {
    override fun encode(writer: BsonWriter, value: User, encoderContext: EncoderContext) {
        writer.writeStartDocument()
        writer.writeObjectId("_id", value.id)
        writer.writeString("email", value.email)
        writer.writeString("surname", value.surname)
        writer.writeString("firstname", value.firstname)
        writer.writeStartArray("processIds")
        value.processIds.forEach { processId -> writer.writeString(processId) }
        writer.writeEndArray()
        writer.writeEndDocument()
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): User {
        reader.readStartDocument()
        val id = reader.readObjectId("_id")
        val email = reader.readString("email")
        val surname = reader.readString("surname")
        val firstname = reader.readString("firstname")
        val processIds = mutableListOf<String>()
        reader.readStartArray()
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            processIds.add(reader.readString())
        }
        reader.readEndArray()
        reader.readEndDocument()

        return User(id, email, surname, firstname, processIds)
    }

    override fun getEncoderClass(): Class<User> {
        return User::class.java
    }
}