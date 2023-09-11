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

    fun handleGetProcessById(id: ObjectId) : Process = processRepository.getProcessById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find process with id ${id.toHexString()}")

    fun handleStartProcess(userId: ObjectId, lang: String) : Question {

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

        // TODO: switch to, getQuestionFromChatGPT
        val question = Question(
            id = ObjectId(),
            processId = process.id,
            question = "First Question",
            answerChoices = listOf("this", "is", "just", "a", "mock", "question"),
            index = 0,
            date = Date.from(Instant.now()),
        )

        questionRepository.createQuestion(question).also {
            if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create question. This is a error caused by internal MongoDB Database.")
        }

        return question
    }

    fun handleResponse(processId: ObjectId, choice: String) : Response {

        val process = processRepository.getProcessById(processId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find process with id ${processId.toHexString()}")

        val questions = questionRepository.getAllQuestionsByProcessId(processId)!!
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

        if (requestIndex > 8) {

            val idea = Idea(
                id = ObjectId(),
                processId = process.id,
                userId = process.userId,
                title = "Your final project idea",
                description = "this is just a mock idea",
                date = Date.from(Instant.now())
            )

            ideaRepository.createIdea(idea).also {
                if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create idea. This is a error caused by internal MongoDB Database.")
            }

            return idea

        } else {

            val question = Question(
                id = ObjectId(),
                processId = process.id,
                question = "Another question",
                answerChoices = listOf("this", "is", "just", "a", "mock", "question"),
                index = requestIndex + 1,
                date = Date.from(Instant.now()),
            )

            questionRepository.createQuestion(question).also {
                if (!it) throw ResponseStatusException(HttpStatus.CONFLICT, "Can't create question. This is a error caused by internal MongoDB Database.")
            }

            return question

        }

    }

}