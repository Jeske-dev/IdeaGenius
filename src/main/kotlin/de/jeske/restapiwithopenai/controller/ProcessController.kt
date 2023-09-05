package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.entities.*
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.Date
import java.util.Random

@RestController
@RequestMapping("/process")
class ProcessController {

    @GetMapping
    fun getProcess(
        @RequestParam id: String
    ) : Process? {

        // TODO: get process from database

        return Process(ObjectId(id), ObjectId(), "de", Date.from(Instant.now()))

    }

    @PostMapping("/start")
    fun startProcess(
        @RequestParam id: String,
        @RequestParam lang: String
    ) : Question? {

        val newProcess = Process(ObjectId(), ObjectId(id), lang, Date.from(Instant.now()))

        val currentRequest = Request(
            id = ObjectId(),
            processId = newProcess.id,
            responseId = null,
            choice = null,
            date = Date.from(Instant.now())
        )

        // TODO: save new process and request

        return Question(
            id = ObjectId(),
            processId = newProcess.id,
            requestId = currentRequest.id,
            date = Date.from(Instant.now()),
            question = "Do you thing this is an error?",
            answerChoices = listOf("Yes", "Maybe, nobody knows...", "100% No"),
        )

    }

    @PostMapping("/response")
    fun response(
        @RequestParam id: String,
        @RequestParam choice: String
    ) : Response? {

        val currentProcess = Process(ObjectId(), ObjectId(), "de", Date.from(Instant.now()))

        val previousResponse = Question(
            id = ObjectId(),
            processId = currentProcess.id,
            requestId = ObjectId(),
            date = Date.from(Instant.now()),
            question = "Do you thing this is an error?",
            answerChoices = listOf("Yes", "Maybe, nobody knows...", "98% No"),
        )

        val currentRequest = Request(
            id = ObjectId(),
            processId = currentProcess.id,
            responseId = previousResponse.id,
            date = Date.from(Instant.now()),
            choice = choice,
        )

        // TODO: save new process and request

        return if (Random().nextInt(2) == 1) {
            Idea(
                id = ObjectId(),
                processId = currentProcess.id,
                requestId = currentRequest.id,
                date = Date.from(Instant.now()),
                userId = currentProcess.userId,
                title = "REST API in Kotlin with MongoDB and OpenAI",
                description = "Develop a RESTful API with Spring Boot in Kotlin. Data should be stored in a MongoDB Database. TIPP: Use MongoDB ATlas, a cloud-based database. Process data with ChatGPT. There is a free API. The topic is left to you!",
            )
        } else Question(
            id = ObjectId(),
            processId = currentProcess.id,
            requestId = currentRequest.id,
            date = Date.from(Instant.now()),
            question = "Ok, and do you think I can do mistakes?",
            answerChoices = listOf("No, because you are a computer", "NOOOOOOOO!", "I dont know"),
        )

    }

}