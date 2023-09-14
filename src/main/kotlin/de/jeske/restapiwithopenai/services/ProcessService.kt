package de.jeske.restapiwithopenai.services

import de.jeske.restapiwithopenai.repositories.IdeaRepository
import de.jeske.restapiwithopenai.repositories.RequestRepository
import de.jeske.restapiwithopenai.modells.*
import de.jeske.restapiwithopenai.repositories.QuestionRepository
import de.jeske.restapiwithopenai.repositories.ProcessRepository
import de.jeske.restapiwithopenai.repositories.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.Date

@Component
class ProcessService {

    @Autowired
    private lateinit var processRepository: ProcessRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @Autowired
    private lateinit var requestRepository: RequestRepository

    @Autowired
    private lateinit var ideaRepository: IdeaRepository

    @Autowired
    private lateinit var userRepository: UserRepository


    @Autowired
    private lateinit var chatGPTService: ChatGPTService

    fun getProcessById(id: ObjectId) : Process = processRepository.getProcessById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find process with id ${id.toHexString()}")

    suspend fun startProcess(userId: ObjectId, lang: String) : Question {

        val user = userRepository.getUserById(userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user with id ${userId.toHexString()}")

        val process = Process(
            ObjectId(),
            userId,
            lang,
            0,
            Date.from(Instant.now())
        )

        val question = getQuestionFromChatGPT(process)
        process.length++

        // (data has to be saved after the request to OpenAI API -> if server based error at OpenAI occurs, current data won't be saved -> user can repeat his step)
        val userProcesses = user.processIds.toMutableList().apply {add(process.id)}
        userRepository.updateUser(user.copy(processIds = userProcesses)).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't update users list of process ids. This is a error caused by internal MongoDB Database.")
        }
        processRepository.createProcess(process).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create process. This is a error caused by internal MongoDB Database.")
        }
        questionRepository.createQuestion(question).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create question. This is a error caused by internal MongoDB Database.")
        }

        return question
    }

    suspend fun continueProcess(processId: ObjectId, choice: String) : Response {

        val process = processRepository.getProcessById(processId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find process with id ${processId.toHexString()}")

        val request = Request(
            ObjectId(),
            process.id,
            choice,
            process.length + 1,
            Date.from(Instant.now())
        )
        process.length++

        requestRepository.createRequest(request).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create request. This is a error caused by internal MongoDB Database.")
        }

        val response: Response
        if (process.length > 7) {

            val idea = getIdeaFromChatGPT(process)

            ideaRepository.createIdea(idea).also {
                if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create idea. This is a error caused by internal MongoDB Database.")
            }

            response = idea

        } else {

            val question = getQuestionFromChatGPT(process)

            questionRepository.createQuestion(question).also {
                if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create question. This is a error caused by internal MongoDB Database.")
            }

            response = question

        }
        process.length += 1

        // (data has to be saved after the request to OpenAI API -> if server based error at OpenAI occurs, current data won't be saved -> user can repeat his step)
        processRepository.updateProcess(process).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't update process. This is a error caused by internal MongoDB Database.")
        }

        return response

    }

    private suspend fun getQuestionFromChatGPT(process: Process): Question {
        val questions = questionRepository.getAllQuestionsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find questions with processId ${process.id.toHexString()}")
        val requests = requestRepository.getAllRequestsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find requests with processId ${process.id.toHexString()}")

        val questionChatGPTDTO = chatGPTService.getQuestion(questions, requests, process.lang)
        return Question(
            ObjectId(),
            process.id,
            questionChatGPTDTO.question,
            questionChatGPTDTO.questionTopic,
            questionChatGPTDTO.answerChoices,
            process.length + 1,
            Date.from(Instant.now())
        )
    }

    private suspend fun getIdeaFromChatGPT(process: Process): Idea {
        val questions = questionRepository.getAllQuestionsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find questions with processId ${process.id.toHexString()}")
        val requests = requestRepository.getAllRequestsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find requests with processId ${process.id.toHexString()}")

        val ideaChatGPTDTO = chatGPTService.getIdea(questions, requests, process.lang)
        return Idea(
            ObjectId(),
            process.id,
            process.userId,
            ideaChatGPTDTO.title,
            ideaChatGPTDTO.description,
            Date.from(Instant.now())
        )
    }

}