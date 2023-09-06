package de.jeske.restapiwithopenai.repositories

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import de.jeske.restapiwithopenai.MongoDBClient
import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository {

    @Autowired
    private lateinit var mongoDBClient: MongoDBClient

    fun getUserById(id: ObjectId) : User? {
        val client = mongoDBClient.getClient()?:return null
        val db = client.getDatabase("development")
        val collection = db.getCollection("users", UserEntity::class.java)

        val byId = Filters.eq(UserEntity::id.name, id)

        val result = collection.find(byId)

        return result.first()?.toUser()
    }
    // getUsers

    fun getUsers(): List<User>? {
        val client = mongoDBClient.getClient()?:return null
        val db = client.getDatabase("development")
        val collection = db.getCollection("users", UserEntity::class.java)

        val result = collection.find()

        return result.toList().map { it.toUser() }
    }

    // createUser

    fun createUser(user: User): Boolean {
        val client = mongoDBClient.getClient()?:return false
        val db = client.getDatabase("development")
        val collection = db.getCollection("users", UserEntity::class.java)

        val result = collection.insertOne(UserEntity(user))

        return result.wasAcknowledged()
    }

    // updateUser

    fun updateUser(user: User): Boolean {
        val client = mongoDBClient.getClient()?:return false
        val db = client.getDatabase("development")
        val collection = db.getCollection("users", UserEntity::class.java)

        val byId = Filters.eq(UserEntity::id.name, user.id)

        val updates = Updates.combine(
            Updates.set(UserEntity::email.name, user.email),
            Updates.addToSet(UserEntity::surname.name, user.surname),
            Updates.addToSet(UserEntity::firstname.name, user.firstname),
            Updates.addEachToSet(UserEntity::processIds.name,user.processIds)
        )

        val result = collection.updateOne(byId, updates)

        return result.wasAcknowledged()
    }

    // deleteUser

    fun deleteUser(id: ObjectId): Boolean {
        val client = mongoDBClient.getClient()?:return false
        val db = client.getDatabase("development")
        val collection = db.getCollection("users", UserEntity::class.java)

        val byId = Filters.eq(UserEntity::id.name, id)

        val result = collection.deleteOne(byId)

        return result.wasAcknowledged()
    }

}