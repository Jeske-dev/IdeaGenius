package de.jeske.restapiwithopenai.services

import de.jeske.restapiwithopenai.dtos.UserDTO
import de.jeske.restapiwithopenai.dtos.UserIdDTO
import de.jeske.restapiwithopenai.modells.User
import de.jeske.restapiwithopenai.repositories.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun handleGetUserById(userId: ObjectId) : User = userRepository.getUserById(userId)
        ?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user with id ${userId.toHexString()}")

    fun handleCreateUser(user: User) : Boolean = userRepository.createUser(user).also {
        if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create user")
    }

    fun handleUpdateUser(userDTO: UserDTO) : User {
        val userId = ObjectId(userDTO.id)
        val originalUser = userRepository.getUserById(userId)
            ?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user with id ${userId.toHexString()}")
        val updatedUser = originalUser.copy(
            email = userDTO.email ?: originalUser.email,
            surname = userDTO.surname ?: originalUser.surname,
            firstname = userDTO.firstname ?: originalUser.firstname
        )
        val acknowledged = userRepository.updateUser(updatedUser)
        return if (acknowledged) {
            updatedUser
        } else throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't upgrade user. This is a error caused by internal MongoDB Database.")
    }

    fun handleDeleteUser(userId: ObjectId) : Boolean = userRepository.deleteUser(userId).also {
        if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't delete user")
    }

}