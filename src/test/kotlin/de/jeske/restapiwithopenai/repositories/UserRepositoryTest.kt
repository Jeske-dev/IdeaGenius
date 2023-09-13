package de.jeske.restapiwithopenai.repositories

import de.jeske.restapiwithopenai.modells.User
import de.jeske.restapiwithopenai.repositories.clients.MongoDBClient
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class UserRepositoryTest {

    private val userRepository = UserRepository(MongoDBClient)

    @Test
    fun getUserById() {
        val testId = ObjectId("64fb0d02c9d972019fa88eb6")
        val user = userRepository.getUserById(testId)
        assertNotNull(user)
        val expectedEmail = "userOne@tester.de"
        assertEquals(expectedEmail, user?.email)
    }

    @Test
    fun getUsers() {
        val users = userRepository.getUsers()
        assertNotNull(users)
        assertNotNull(users?.first())
        println(users?.first())
    }

    @Test
    fun createUser() {
        val user = User(
            ObjectId(),
            "userThree@tester.de",
            "User",
            "Three",
            listOf()
        )
        val acknowledged = userRepository.createUser(user)
        assert(acknowledged)
    }

    @Test
    fun createAndDeleteUser() {
        val user = User(
            ObjectId(),
            "createDeleteuser@tester.de",
            "User",
            "Create Delete"
        )
        val acknowledged = userRepository.createUser(user)
        assert(acknowledged)

        if (acknowledged) {
            val acknowledged2 = userRepository.deleteUser(user.id)
            assert(acknowledged2)
        }
    }

    @Test
    fun updateUser() {
        val updateCounterUserId = ObjectId("64fb08906ba2b90d73b22f6d")
        val user = userRepository.getUserById(updateCounterUserId)

        val acknowledged = user != null
        assertNotNull(acknowledged)

        if (acknowledged) {
            val updatedUser = user!!.copy(
                firstname = "${user.firstname} I"
            )
            val acknowledged2 = userRepository.updateUser(updatedUser)
            assert(acknowledged2)
        }
    }

}