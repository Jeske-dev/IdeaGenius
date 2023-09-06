package de.jeske.restapiwithopenai

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.eq
import de.jeske.restapiwithopenai.codecs.UserEntityCodec
import de.jeske.restapiwithopenai.entities.User
import org.bson.BsonInt64
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.bson.types.ObjectId
import java.lang.Exception

object MongoDBClient {
    fun init() : MongoClient? {

        val uri = "mongodb+srv://dev:082m5Zip4JWiVV4U@cluster0.3hlx91e.mongodb.net/?retryWrites=true&w=majority"
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
        val client = MongoDBClient.init() ?: return null
        val database = client.getDatabase("TestData")
        val collection = database.getCollection("users", User::class.java)
        val result = collection.find()

        val user = result.first()

        client.close()

        return user
    }

    fun insertTestUser() : Boolean {
        try {
            val client = MongoDBClient.init() ?: return false
            val database = client.getDatabase("TestData")
            val collection = database.getCollection("users", User::class.java)

            val testUser = User(id = ObjectId(), email =  "testmail@mail.de", surname = "Zufall", firstname = "Rheiner")

            return collection.insertOne(testUser).wasAcknowledged()
        } catch (e: Exception) {
            return false
        }
    }
}

//collection.find(Document("_id", "64f5cb497b4d45c7c950c2e0"))
//collection.aggregate(listOf())