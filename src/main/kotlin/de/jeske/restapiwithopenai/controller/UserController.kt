package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.dtos.UserDTO
import de.jeske.restapiwithopenai.dtos.UserIdDTO
import de.jeske.restapiwithopenai.entities.UserEntity
import org.bson.types.ObjectId
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

    @GetMapping
    fun getUser(@RequestParam id: String) : UserDTO {

        // all of this should be done in a service

        // TODO: ... get User from database ...

        val user = UserEntity(ObjectId(), "Pasti", "Andi", "andipasti@tester.lol")

        return UserDTO(user)
    }

    @PostMapping
    fun createUser(
        @RequestParam email: String,
        @RequestParam surname: String,
        @RequestParam firstname: String
    ): UserIdDTO? {

        val user = UserEntity(
            email = email,
            surname = surname,
            firstname = firstname
        )

        // all of this should be done in a service

        // TODO: ... add User to database

        return UserIdDTO(user)

    }

    @PutMapping
    fun updateUser(
        @RequestParam id: String,
        @RequestParam email: String?,
        @RequestParam surname: String?,
        @RequestParam firstname: String?,
    ) : UserDTO? {

        // all of this should be done in a service
        // TODO: ... get User by Id from database

        val oldUser = UserEntity(
            surname = "Milli",
            firstname = "Liter",
            email = "milliliter@tester.lol"
        )

        val newUser = oldUser.copy(
            email = email ?: oldUser.email,
            surname = surname ?: oldUser.surname,
            firstname = firstname ?: oldUser.firstname
        )

        return UserDTO(newUser)

    }

    @DeleteMapping
    fun deleteUser(
        @RequestParam id: String
    ) : Boolean? {


        // all of this should be done in a service
        // TODO: ... get User by Id from database and delete him

        return true

    }

}