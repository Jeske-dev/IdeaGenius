import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jeske.restapiwithopenai.entities.RequestEntity
import de.jeske.restapiwithopenai.modells.Request
import de.jeske.restapiwithopenai.repositories.MongoDBClient
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class RequestRepository @Autowired constructor(private val mongoDBClient: MongoDBClient) {

    private val databaseName = "TestData"

    fun getRequestById(id: ObjectId): Request? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("requests", RequestEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.find(byId)
        val request = result.firstOrNull()?.toRequest()

        client.close()
        return request
    }

    fun getAllRequestsByProcessId(processId: ObjectId): List<Request>? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("requests", RequestEntity::class.java)
        val byProcessId = Filters.eq("processId", processId)

        val result = collection.find(byProcessId)
        val requests = result.toList().map { it.toRequest() }

        client.close()
        return requests
    }

    fun createRequest(request: Request): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("requests", RequestEntity::class.java)

        val result = collection.insertOne(RequestEntity(request))
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun updateRequest(request: Request): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("requests", RequestEntity::class.java)
        val byId = Filters.eq("_id", request.id)

        // Hier fehlt die Update-Logik, da 'choice' nicht in der Request-Entität enthalten ist.

        val result = collection.updateOne(byId, Updates.set("choice", request.choice))
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun deleteRequest(id: ObjectId): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("requests", RequestEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.deleteOne(byId)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }
}
