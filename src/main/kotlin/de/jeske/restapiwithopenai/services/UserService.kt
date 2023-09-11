package de.jeske.restapiwithopenai.services

import de.jeske.restapiwithopenai.dtos.UserDTO
import de.jeske.restapiwithopenai.dtos.UserIdDTO
import de.jeske.restapiwithopenai.modells.User
import de.jeske.restapiwithopenai.repositories.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun handleGetUserById(userId: ObjectId) : User? = userRepository.getUserById(userId)

    fun handleCreateUser(user: User) : Boolean = userRepository.createUser(user)

    fun handleUpdateUser(userDTO: UserDTO) : User? {
        val userId = ObjectId(userDTO.id)
        val originalUser = userRepository.getUserById(userId) ?: return null
        val updatedUser = originalUser.copy(
            email = userDTO.email ?: originalUser.email,
            surname = userDTO.surname ?: originalUser.surname,
            firstname = userDTO.firstname ?: originalUser.firstname
        )
        val acknowledged = userRepository.updateUser(updatedUser)
        return if (acknowledged) updatedUser else null
    }

    fun handleDeleteUser(userId: ObjectId) : Boolean = userRepository.deleteUser(userId)

}