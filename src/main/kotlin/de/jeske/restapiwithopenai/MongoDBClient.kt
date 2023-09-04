package de.jeske.restapiwithopenai

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.eq
import de.jeske.restapiwithopenai.entities.User
import org.bson.BsonInt64
import org.bson.Document
import org.bson.types.ObjectId

object MongoDBClient {
    fun init() : MongoClient? {

        val uri = "mongodb+srv://dev:082m5Zip4JWiVV4U@cluster0.3hlx91e.mongodb.net/?retryWrites=true&w=majority"
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val settings = MongoClientSettings.builder()
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

    fun getTestUser() : User? {
        val client = MongoDBClient.init() ?: return null
        val database = client.getDatabase("TestData")
        val collection = database.getCollection("users")
        val result = collection.withDocumentClass<User>(User::class.java)
            .find(eq("_id", ObjectId("64f5cb497b4d45c7c950c2e0")))

        result.forEach { println(it) }

        client.close()

        return result.first()
    }
}