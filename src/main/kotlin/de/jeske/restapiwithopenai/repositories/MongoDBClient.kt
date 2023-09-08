package de.jeske.restapiwithopenai.repositories

import GenericCodec
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.jeske.restapiwithopenai.entities.*
import de.jeske.restapiwithopenai.repositories.codecs.*
import org.bson.BsonInt64
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.springframework.stereotype.Component

@Component
object MongoDBClient {

    //@Autowired
    //private lateinit var args : ApplicationArguments

    // just for test
    private const val _uri = "mongodb+srv://dev:082m5Zip4JWiVV4U@cluster0.3hlx91e.mongodb.net/?retryWrites=true&w=majority"

    private val codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromCodecs(

            GenericCodec(UserEntity::class.java, listOf("_id", "email", "surname", "firstname")),
            GenericCodec(ProcessEntity::class.java, listOf("_id", "userId", "lang", "date")),
            GenericCodec(RequestEntity::class.java, listOf("_id", "processId", "choice", "index", "date")),
            GenericCodec(QuestionEntity::class.java, listOf("_id", "processId", "question", "answerChoices", "index", "date")),
            GenericCodec(IdeaEntity::class.java, listOf("_id", "processId", "userId", "title", "description", "date"))
        )
    )
    private val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()

    fun getClient() : MongoClient? {

        // for testing
        val uri = _uri
        //val uri = args.getOptionValues("uri").first() as String

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
            println("Pinged your deployment. You successfully connected to MongoDB!")
            mongoClient
        } catch (e: MongoException) {
            System.err.println(e)
            null
        }
    }
}