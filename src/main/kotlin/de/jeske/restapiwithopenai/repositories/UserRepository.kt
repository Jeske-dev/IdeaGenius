package de.jeske.restapiwithopenai.repositories

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository @Autowired constructor(private val mongoDBClient: MongoDBClient) {

    private val databaseName = "TestData"

    fun getUserById(id: ObjectId) : User? {
        val client = mongoDBClient.getClient()?:return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("users", UserEntity::class.java)
        val byId = Document("_id", id)

        val result = collection.find(byId)
        val user = result.first()?.toUser()

        client.close()
        return user
    }
    // getUsers

    fun getUsers(): List<User>? {
        val client = mongoDBClient.getClient()?:return null
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("users", UserEntity::class.java)

        val result = collection.find()
        val users = result.toList().map { it.toUser() }

        client.close()
        return users
    }

    // createUser

    fun createUser(user: User): Boolean {
        val client = mongoDBClient.getClient()?:return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("users", UserEntity::class.java)

        val result = collection.insertOne(UserEntity(user))
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    // updateUser

    fun updateUser(user: User): Boolean {
        val client = mongoDBClient.getClient()?:return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("users", UserEntity::class.java)

        val byId = Document("_id", user.id)

        val updates = Updates.combine(
            Updates.set(UserEntity::email.name, user.email),
            Updates.set(UserEntity::surname.name, user.surname),
            Updates.set(UserEntity::firstname.name, user.firstname),
            Updates.addEachToSet(UserEntity::processIds.name,user.processIds)
        )

        val result = collection.updateOne(byId, updates)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

    // deleteUser

    fun deleteUser(id: ObjectId): Boolean {
        val client = mongoDBClient.getClient()?:return false
        val db = client.getDatabase(databaseName)
        val collection = db.getCollection("users", UserEntity::class.java)

        val byId = Document("_id", id)

        val result = collection.deleteOne(byId)
        val wasAcknowledged = result.wasAcknowledged()

        client.close()
        return wasAcknowledged
    }

}