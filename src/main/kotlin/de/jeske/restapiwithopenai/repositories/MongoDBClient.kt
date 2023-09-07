package de.jeske.restapiwithopenai.repositories

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.jeske.restapiwithopenai.codecs.UserEntityCodec
import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import org.bson.BsonInt64
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
object MongoDBClient {

    //@Autowired
    //private lateinit var args : ApplicationArguments

    // just for test
    private const val _uri = "mongodb+srv://dev:082m5Zip4JWiVV4U@cluster0.3hlx91e.mongodb.net/?retryWrites=true&w=majority"


    fun getClient() : MongoClient? {


        // for testing
        val uri = _uri
        //val uri = args.getOptionValues("uri").first() as String

        val codecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromCodecs(UserEntityCodec())
        )
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()

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

    fun getRandomTestUser() : User? {
        val client = getClient() ?: return null
        val database = client.getDatabase("TestData")
        val collection = database.getCollection("users", UserEntity::class.java)
        val result = collection.find()

        val user = result.first()?.toUser()

        return user
    }

    fun insertTestUser() : Boolean {
        try {
            val client = getClient() ?: return false
            val database = client.getDatabase("TestData")
            val collection = database.getCollection("users", UserEntity::class.java)

            val testUser = User(id = ObjectId(), email =  "testmail@mail.de", surname = "Zufall", firstname = "Rheiner")

            return collection.insertOne(UserEntity(testUser)).wasAcknowledged()
        } catch (e: Exception) {
            return false
        }
    }
}