package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.dtos.UserDTO
import de.jeske.restapiwithopenai.dtos.UserIdDTO
import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import de.jeske.restapiwithopenai.services.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun getUser(@RequestParam id: String) : UserDTO {

        val objectId = ObjectId(id)

        val user = userService.handleGetUserById(objectId)

        return UserDTO(user)
    }

    @PostMapping
    fun createUser(
        @RequestParam email: String,
        @RequestParam surname: String,
        @RequestParam firstname: String
    ): UserIdDTO? {

        val user = User(
            email = email,
            surname = surname,
            firstname = firstname
        )

        val acknowledged = userService.handleCreateUser(user)

        return UserIdDTO(user)

    }

    @PutMapping
    fun updateUser(
        @RequestParam id: String,
        @RequestParam email: String,
        @RequestParam surname: String,
        @RequestParam firstname: String,
    ) : UserDTO? {

        val userDTO = UserDTO(id, email, surname, firstname)

        val user = userService.handleUpdateUser(userDTO)

        return UserDTO(user)

    }

    @DeleteMapping
    fun deleteUser(
        @RequestParam id: String
    ) : Boolean? {

        val objectId = ObjectId(id)

        return userService.handleDeleteUser(objectId)

    }

}