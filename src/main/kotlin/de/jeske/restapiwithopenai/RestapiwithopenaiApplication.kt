package de.jeske.restapiwithopenai

import de.jeske.restapiwithopenai.controller.ChatGPTController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@FlowPreview
@ExperimentalCoroutinesApi
class RestapiwithopenaiApplication

fun main(args: Array<String>) {
    runApplication<RestapiwithopenaiApplication>(*args)
}