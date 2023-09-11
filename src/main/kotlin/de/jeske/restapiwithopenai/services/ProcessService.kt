package de.jeske.restapiwithopenai.services

import de.jeske.restapiwithopenai.repositories.IdeaRepository
import de.jeske.restapiwithopenai.repositories.RequestRepository
import de.jeske.restapiwithopenai.modells.*
import de.jeske.restapiwithopenai.repositories.QuestionRepository
import de.jeske.restapiwithopenai.repositories.ProcessRepository
import de.jeske.restapiwithopenai.repositories.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
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

    fun handleGetProcessById(id: ObjectId) : Process? = processRepository.getProcessById(id)

    fun handleStartProcess(userId: ObjectId, lang: String) : Question? {

        val user = userRepository.getUserById(userId) ?: return null

        val process = Process(
            ObjectId(),
            userId,
            lang,
            Date.from(Instant.now())
        )

        val userProcesses = user.processIds.toMutableList().apply {
            add(process.id)
        }
        userRepository.updateUser(user.copy(processIds = userProcesses))

        processRepository.createProcess(process)

        // TODO: switch to, getQuestionFromChatGPT
        val question = Question(
            id = ObjectId(),
            processId = process.id,
            question = "First Question",
            answerChoices = listOf("this", "is", "just", "a", "mock", "question"),
            index = 0,
            date = Date.from(Instant.now()),
        )

        questionRepository.createQuestion(question)

        return question
    }

    fun handleResponse(processId: ObjectId, choice: String) : Response? {

        val process = processRepository.getProcessById(processId) ?: return null

        val questions = questionRepository.getAllQuestionsByProcessId(processId)!!
        val requestIndex = questions.size*2-1

        val request = Request(
            ObjectId(),
            process.id,
            choice,
            requestIndex,
            Date.from(Instant.now())
        )

        requestRepository.createRequest(request)

        if (requestIndex > 8) {

            val idea = Idea(
                id = ObjectId(),
                processId = process.id,
                userId = process.userId,
                title = "Your final project idea",
                description = "this is just a mock idea",
                date = Date.from(Instant.now())
            )

            ideaRepository.createIdea(idea)

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

            questionRepository.createQuestion(question)

            return question

        }

    }

}