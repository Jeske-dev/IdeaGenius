package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.entities.Process
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.Date

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

}