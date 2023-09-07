package de.jeske.restapiwithopenai.repositories

import de.jeske.restapiwithopenai.modells.User
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserRepositoryTest {

    private val userRepository = UserRepository(MongoDBClient)

    @Test
    fun getUserById() {
        val testId = ObjectId("64f5ce6e7b4d45c7c950c2e3")
        val user = userRepository.getUserById(testId)
        assertNotNull(user)
        val expectedEmail = "hannesszeski@gmail.com"
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
    fun createAndDeleteUser() {
        val user = User(
            ObjectId(),
            "milliliter@tester.lol",
            "liter",
            "milli"
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
        val user = User(
            ObjectId("64f5ce6e7b4d45c7c950c2e3"),
            "hannesszeski@gmail.com",
            "von Szeski",
            "Hannes"
        )
        val acknowledged = userRepository.updateUser(user)
        assert(acknowledged)
    }

}