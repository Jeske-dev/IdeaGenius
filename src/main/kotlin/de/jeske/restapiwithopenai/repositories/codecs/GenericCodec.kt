import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.types.ObjectId
import java.util.Date
import kotlin.reflect.KParameter
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@Suppress("IMPLICIT_CAST_TO_ANY")
class GenericCodec<T : Any>(private val clazz: Class<T>, val propertiesOrder: List<String>) : Codec<T> {

    override fun encode(writer: BsonWriter, value: T, encoderContext: EncoderContext) {
        writer.writeStartDocument()

        propertiesOrder.forEach { propertyName ->
            val field = clazz.kotlin.memberProperties.firstOrNull { it.name == propertyName }
            if (field != null) {
                val fieldName = field.name
                val fieldValue = field.get(value)

                when {
                    field.returnType.isSubtypeOf(ObjectId::class.createType()) -> writer.writeObjectId(
                        fieldName,
                        fieldValue as ObjectId
                    )

                    field.returnType.isSubtypeOf(String::class.createType()) -> writer.writeString(
                        fieldName,
                        fieldValue as String
                    )

                    field.returnType.isSubtypeOf(Date::class.createType()) -> writer.writeDateTime(
                        fieldName,
                        (fieldValue as Date).time
                    )

                    field.returnType.isSubtypeOf(List::class.createType(listOf(KTypeProjection.invariant(ObjectId::class.createType())))) -> {
                        writer.writeStartArray(fieldName)
                        (fieldValue as List<*>).forEach { writer.writeObjectId(it as ObjectId) }
                        writer.writeEndArray()
                    }

                    field.returnType.isSubtypeOf(List::class.createType(listOf(KTypeProjection.invariant(String::class.createType())))) -> {
                        writer.writeStartArray(fieldName)
                        (fieldValue as List<*>).forEach { writer.writeString(it as String) }
                        writer.writeEndArray()
                    }

                    field.returnType.isSubtypeOf(Int::class.createType()) -> writer.writeInt32(fieldName, fieldValue as Int)
                    field.returnType.isSubtypeOf(Double::class.createType()) -> writer.writeDouble(
                        fieldName,
                        fieldValue as Double
                    )

                    else -> {
                        throw Exception("Unsupported type: ${field.returnType}")
                    }
                }
            }
        }

        writer.writeEndDocument()
    }



    override fun decode(reader: BsonReader, decoderContext: DecoderContext): T {
        reader.readStartDocument()
        val primaryConstructor = clazz.kotlin.primaryConstructor
            ?: throw IllegalArgumentException("Class $clazz does not have a primary constructor")
        val propertiesByName = clazz.kotlin.memberProperties.associateBy { it.name }

        val args = mutableMapOf<KParameter, Any?>()

        primaryConstructor.parameters.forEach { parameter ->
            val fieldName = parameter.name
            val field = propertiesByName[fieldName]
                ?: throw IllegalArgumentException("No field found for $fieldName")
            val fieldValue = when {
                field.returnType.isSubtypeOf(ObjectId::class.createType()) -> reader.readObjectId(fieldName)
                field.returnType.isSubtypeOf(String::class.createType()) -> reader.readString(fieldName)
                field.returnType.isSubtypeOf(List::class.createType(listOf(KTypeProjection.invariant(ObjectId::class.createType())))) -> {
                    val list = mutableListOf<ObjectId>()
                    try {
                        reader.readStartArray()
                        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                            list.add(reader.readObjectId())
                        }
                        reader.readEndArray()
                    } catch (e: java.lang.Exception) {
                        println("Codec Exception: cant fetch list of objectIds")
                        println(e.message)
                    }
                    list
                }
                field.returnType.isSubtypeOf(List::class.createType(listOf(KTypeProjection.invariant(String::class.createType())))) -> {
                    val list = mutableListOf<String>()
                    try {
                        reader.readStartArray()
                        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                            list.add(reader.readString())
                        }
                        reader.readEndArray()
                    } catch (e: java.lang.Exception) {
                        println("Codec Exception: cant fetch list of string")
                        println(e.message)
                    }
                    list
                }
                field.returnType.isSubtypeOf(Date::class.createType()) -> Date(reader.readDateTime(fieldName))
                field.returnType.isSubtypeOf(Int::class.createType()) -> reader.readInt32(fieldName)
                field.returnType.isSubtypeOf(Double::class.createType()) -> reader.readDouble(fieldName)
                else -> {
                    throw Exception("Unsupported type: ${field.returnType}")
                }
            }
            args[parameter] = fieldValue
        }
        reader.readEndDocument()
        return primaryConstructor.callBy(args)
    }


    override fun getEncoderClass(): Class<T> {
        return clazz
    }
}