import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jeske.restapiwithopenai.entities.IdeaEntity
import de.jeske.restapiwithopenai.modells.Idea
import de.jeske.restapiwithopenai.repositories.MongoDBClient
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class IdeaRepository @Autowired constructor(private val mongoDBClient: MongoDBClient) {

    private val databaseName = "TestData"

    fun getIdeaById(id: ObjectId): Idea? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("ideas", IdeaEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.find(byId)
        val idea = result.firstOrNull()?.toIdea()

        client.close()
        return idea
    }

    fun getAllIdeasByProcessId(processId: ObjectId): List<Idea>? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("ideas", IdeaEntity::class.java)
        val byProcessId = Filters.eq("processId", processId)

        val result = collection.find(byProcessId)
        val ideas = result.toList().map { it.toIdea() }

        client.close()
        return ideas
    }

    fun createIdea(idea: Idea): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("ideas", IdeaEntity::class.java)

        val result = collection.insertOne(IdeaEntity(idea))
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun updateIdea(idea: Idea): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("ideas", IdeaEntity::class.java)
        val byId = Filters.eq("_id", idea.id)

        val updates = Updates.combine(
            Updates.set(IdeaEntity::processId.name, idea.processId),
            Updates.set(IdeaEntity::requestId.name, idea.requestId),
            Updates.set(IdeaEntity::date.name, idea.date),
            Updates.set(IdeaEntity::userId.name, idea.userId),
            Updates.set(IdeaEntity::title.name, idea.title),
            Updates.set(IdeaEntity::description.name, idea.description)
        )

        val result = collection.updateOne(byId, updates)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun deleteIdea(id: ObjectId): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("ideas", IdeaEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.deleteOne(byId)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }
}
