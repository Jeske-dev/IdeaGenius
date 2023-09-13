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
            Date.from(Instant.now())
        )

        val userProcesses = user.processIds.toMutableList().apply {
            add(process.id)
        }
        userRepository.updateUser(user.copy(processIds = userProcesses)).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't update users list of process ids. This is a error caused by internal MongoDB Database.")
        }

        processRepository.createProcess(process).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create process. This is a error caused by internal MongoDB Database.")
        }

        val questions = questionRepository.getAllQuestionsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find questions with processId ${process.id.toHexString()}")
        val requests = requestRepository.getAllRequestsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find requests with processId ${process.id.toHexString()}")

        val questionChatGPTDTO = chatGPTService.getQuestion(questions, requests)
        val question = Question(
            ObjectId(),
            process.id,
            questionChatGPTDTO.question,
            questionChatGPTDTO.questionTopic,
            questionChatGPTDTO.answerChoices,
            0,
            Date.from(Instant.now())
        )

        questionRepository.createQuestion(question).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create question. This is a error caused by internal MongoDB Database.")
        }

        return question
    }

    suspend fun continueProcess(processId: ObjectId, choice: String) : Response {

        val process = processRepository.getProcessById(processId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find process with id ${processId.toHexString()}")

        val questions = questionRepository.getAllQuestionsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find questions with processId ${process.id.toHexString()}")

        val requestIndex = questions.size*2-1


        val request = Request(
            ObjectId(),
            process.id,
            choice,
            requestIndex,
            Date.from(Instant.now())
        )

        requestRepository.createRequest(request).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create request. This is a error caused by internal MongoDB Database.")
        }


        val requests = requestRepository.getAllRequestsByProcessId(process.id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find requests with processId ${process.id.toHexString()}")

        if (requestIndex > 8) {

            val ideaChatGPTDTO = chatGPTService.getIdea(questions, requests)
            val idea = Idea(
                ObjectId(),
                process.id,
                process.userId,
                ideaChatGPTDTO.title,
                ideaChatGPTDTO.description,
                Date.from(Instant.now())
            )

            ideaRepository.createIdea(idea).also {
                if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create idea. This is a error caused by internal MongoDB Database.")
            }

            return idea

        } else {

            val questionChatGPTDTO = chatGPTService.getQuestion(questions, requests)
            val question = Question(
                ObjectId(),
                process.id,
                questionChatGPTDTO.question,
                questionChatGPTDTO.questionTopic,
                questionChatGPTDTO.answerChoices,
                requestIndex + 1,
                Date.from(Instant.now())
            )

            questionRepository.createQuestion(question).also {
                if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create question. This is a error caused by internal MongoDB Database.")
            }

            return question

        }

    }

}