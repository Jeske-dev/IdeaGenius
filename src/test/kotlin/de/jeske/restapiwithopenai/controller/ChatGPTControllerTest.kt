package de.jeske.restapiwithopenai.controller

import de.jeske.restapiwithopenai.repositories.mockups.ChatsRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


class ChatGPTControllerTest {

    private var chatGPTController = ChatGPTController()

    @Test
    fun completion() {
        val result = runBlocking {
            chatGPTController.completion(ChatsRepository.getQuestionChat(emptyList()))
        }
        assertNotNull(result)
        println(result)
    }
}