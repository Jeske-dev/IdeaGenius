package de.jeske.restapiwithopenai.repositories

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jeske.restapiwithopenai.entities.ProcessEntity
import de.jeske.restapiwithopenai.modells.Process
import de.jeske.restapiwithopenai.repositories.clients.MongoDBClient
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ProcessRepository @Autowired constructor(private val mongoDBClient: MongoDBClient) {

    private val databaseName = "TestData"

    fun getProcessById(id: ObjectId): Process? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("processes", ProcessEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.find(byId)
        val process = result.firstOrNull()?.toProcess()

        client.close()
        return process
    }

    fun getAllProcessWithUserId(userId: ObjectId): List<Process>? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("processes", ProcessEntity::class.java)
        val byUserId = Filters.eq("userId", userId)

        val result = collection.find(byUserId)
        val processes = result.toList().map { it.toProcess() }

        client.close()
        return processes
    }

    fun createProcess(process: Process): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("processes", ProcessEntity::class.java)

        val result = collection.insertOne(ProcessEntity(process))
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun updateProcess(process: Process): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("processes", ProcessEntity::class.java)
        val byId = Filters.eq("_id", process.id)

        val updates = Updates.combine(
            Updates.set(ProcessEntity::userId.name, process.userId),
            Updates.set(ProcessEntity::lang.name, process.lang),
            Updates.set(ProcessEntity::date.name, process.date)
        )

        val result = collection.updateOne(byId, updates)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun deleteProcess(id: ObjectId): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("processes", ProcessEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.deleteOne(byId)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }
}
