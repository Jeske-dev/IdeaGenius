package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.dtos.IdeaDTO
import de.jeske.restapiwithopenai.dtos.ProcessDTO
import de.jeske.restapiwithopenai.dtos.QuestionDTO
import de.jeske.restapiwithopenai.dtos.ResponseDTO
import de.jeske.restapiwithopenai.modells.Idea
import de.jeske.restapiwithopenai.modells.Question
import de.jeske.restapiwithopenai.services.ProcessService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/process")
class ProcessController {

    @Autowired
    private lateinit var processService: ProcessService

    @GetMapping
    fun getProcess(
        @RequestParam id: String
    ) : ProcessDTO {

        val processId = ObjectId(id)

        val process = processService.getProcessById(processId)

        return ProcessDTO(process)

    }

    @PostMapping("/start")
    suspend fun startProcess(
        @RequestParam id: String,
        @RequestParam lang: String
    ) : QuestionDTO {

        val userId = ObjectId(id)

        val question = processService.startProcess(userId, lang)

        return QuestionDTO(question)

    }

    @PostMapping("/response")
    suspend fun response(
        @RequestParam id: String,
        @RequestParam choice: String
    ) : ResponseDTO {

        val processId = ObjectId(id)

        val response = processService.continueProcess(processId, choice)

        return when (response) {
            is Idea -> IdeaDTO(response)
            is Question -> QuestionDTO(response)
            else -> throw TypeNotPresentException("response", null)
        }

    }

}