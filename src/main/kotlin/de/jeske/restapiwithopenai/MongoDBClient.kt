package de.jeske.restapiwithopenai

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.BsonInt64
import org.bson.Document

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
}