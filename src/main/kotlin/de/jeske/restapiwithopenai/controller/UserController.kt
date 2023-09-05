package de.jeske.restapiwithopenai.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UserController {

    @GetMapping("/")
    fun index() = "Hello!"

}