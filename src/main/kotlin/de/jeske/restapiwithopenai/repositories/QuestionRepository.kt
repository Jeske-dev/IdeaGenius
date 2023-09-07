import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jeske.restapiwithopenai.entities.QuestionEntity
import de.jeske.restapiwithopenai.modells.Question
import de.jeske.restapiwithopenai.repositories.MongoDBClient
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class QuestionRepository @Autowired constructor(private val mongoDBClient: MongoDBClient) {

    private val databaseName = "TestData"

    fun getQuestionById(id: ObjectId): Question? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("questions", QuestionEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.find(byId)
        val question = result.firstOrNull()?.toQuestion()

        client.close()
        return question
    }

    fun getAllQuestionsByProcessId(processId: ObjectId): List<Question>? {
        val client = mongoDBClient.getClient() ?: return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("questions", QuestionEntity::class.java)
        val byProcessId = Filters.eq("processId", processId)

        val result = collection.find(byProcessId)
        val questions = result.toList().map { it.toQuestion() }

        client.close()
        return questions
    }

    fun createQuestion(question: Question): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("questions", QuestionEntity::class.java)

        val result = collection.insertOne(QuestionEntity(question))
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun updateQuestion(question: Question): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("questions", QuestionEntity::class.java)
        val byId = Filters.eq("_id", question.id)

        val updates = Updates.combine(
            Updates.set(QuestionEntity::processId.name, question.processId),
            Updates.set(QuestionEntity::requestId.name, question.requestId),
            Updates.set(QuestionEntity::date.name, question.date),
            Updates.set(QuestionEntity::question.name, question.question),
            Updates.set(QuestionEntity::answerChoices.name, question.answerChoices)
        )

        val result = collection.updateOne(byId, updates)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    fun deleteQuestion(id: ObjectId): Boolean {
        val client = mongoDBClient.getClient() ?: return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("questions", QuestionEntity::class.java)
        val byId = Filters.eq("_id", id)

        val result = collection.deleteOne(byId)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }
}
