package de.jeske.restapiwithopenai.controller

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds

@Component
class ChatGPTController {

    @Value("\${openAi.apiKey}")
    private lateinit var apiKey: String

    private val timeout = Timeout(socket = 60.seconds)
    private val modelId = "gpt-3.5-turbo"
    private val maxTokens = 200

    suspend fun completion(messages: List<ChatMessage>): String? {

        val openAI = OpenAI(
            token = apiKey,
            timeout = timeout,
        )

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId),
            maxTokens = maxTokens,
            messages = messages
        )

        val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
        val content = completion.choices.first().message.content

        openAI.close()

        return content

    }

}