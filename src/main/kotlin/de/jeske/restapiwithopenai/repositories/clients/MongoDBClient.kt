package de.jeske.restapiwithopenai.repositories.clients

import GenericCodec
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.jeske.restapiwithopenai.entities.*
import org.bson.BsonInt64
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MongoDBClient {

    //@Autowired
    //private lateinit var args : ApplicationArguments

    // just for test
    @Value("\${mongo.url}")
    private lateinit var _uri: String

    private val codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromCodecs(

            GenericCodec(UserEntity::class.java, listOf("_id", "email", "surname", "firstname", "processIds")),
            GenericCodec(ProcessEntity::class.java, listOf("_id", "userId", "lang", "length", "date")),
            GenericCodec(RequestEntity::class.java, listOf("_id", "processId", "choice", "index", "date")),
            GenericCodec(QuestionEntity::class.java, listOf("_id", "processId", "question", "questionTopic", "answerChoices", "index", "date")),
            GenericCodec(IdeaEntity::class.java, listOf("_id", "processId", "userId", "title", "description", "date"))
        )
    )
    private val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()

    fun getClient() : MongoClient? {

        val uri = _uri

        val settings = MongoClientSettings.builder()
            .codecRegistry(codecRegistry)
            .applyConnectionString(ConnectionString(uri))
            .serverApi(serverApi)
            .build()

        val mongoClient = MongoClients.create(settings)
        val database = mongoClient.getDatabase("admin")

        return try {
            val command = Document("ping", BsonInt64(1))
            val commandResult = database.runCommand(command)
            mongoClient
        } catch (e: MongoException) {
            System.err.println(e)
            null
        }
    }
}