package de.jeske.restapiwithopenai.services

import com.fasterxml.jackson.databind.ObjectMapper
import de.jeske.restapiwithopenai.Chats
import de.jeske.restapiwithopenai.controller.ChatGPTController
import de.jeske.restapiwithopenai.dtos.IdeaChatGPTDTO
import de.jeske.restapiwithopenai.dtos.QuestionChatGPTDTO
import de.jeske.restapiwithopenai.modells.Question
import de.jeske.restapiwithopenai.modells.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class ChatGPTService {

    @Autowired
    private lateinit var chatGPTController: ChatGPTController

    suspend fun getQuestion(questions: List<Question>, responses: List<Response>): QuestionChatGPTDTO {

        val questionRaw = chatGPTController.completion(
            Chats.generateGetQuestionChat()
        ) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ChatGPT is not responding!")

        return parseStringToQuestionChatGPTDTO(questionRaw)
    }

    /*suspend fun getIdea(processId: ObjectId, userId: ObjectId): Idea {

        val ideaRaw = chatGPTController.completion(listOf())
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ChatGPT is not responding!")

        return parseIdea(
            ideaRaw,
            processId,
            userId
        )

    }

     */

    fun parseStringToQuestionChatGPTDTO(raw: String): QuestionChatGPTDTO {
        val objectMapper = ObjectMapper()
        return try {
            objectMapper.readValue(raw, QuestionChatGPTDTO::class.java)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cant parse raw question string to questionchatgptdto object, which is provided by chatgpt! \n${e.toString()}")
        }
    }

    fun parseStringToIdeaChatGPTDTO(raw: String): IdeaChatGPTDTO {
        val objectMapper = ObjectMapper()
        return try {
            objectMapper.readValue(raw, IdeaChatGPTDTO::class.java)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cant parse raw idea string to ideachatgptdto object, which is provided by chatgpt! \n${e.toString()}")
        }
    }

}