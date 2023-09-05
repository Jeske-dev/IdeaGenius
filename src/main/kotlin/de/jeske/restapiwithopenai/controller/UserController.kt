package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.dtos.UserId
import de.jeske.restapiwithopenai.entities.User
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
    fun getUser(@RequestParam id: String) : User? {

        // all of this should be done in a service

        // TODO: ... get User from database ...

        return User(
            surname = "Pasti",
            firstname = "Andi",
            email = "andipasti@tester.lol"
        )
    }

    @PostMapping
    fun createUser(
        @RequestParam email: String,
        @RequestParam surname: String,
        @RequestParam firstname: String
    ): UserId? {

        val user = User(
            email = email,
            surname = surname,
            firstname = firstname
        )

        // all of this should be done in a service

        // TODO: ... add User to database

        return UserId(user.id)

    }

    @PutMapping
    fun updateUser(
        @RequestParam id: String,
        @RequestParam email: String?,
        @RequestParam surname: String?,
        @RequestParam firstname: String?,
    ) : User? {

        // all of this should be done in a service
        // TODO: ... get User by Id from database

        val oldUser = User(
            surname = "Milli",
            firstname = "Liter",
            email = "milliliter@tester.lol"
        )

        val newUser = oldUser.copy(
            email = email ?: oldUser.email,
            surname = surname ?: oldUser.surname,
            firstname = firstname ?: oldUser.firstname
        )

        return newUser

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