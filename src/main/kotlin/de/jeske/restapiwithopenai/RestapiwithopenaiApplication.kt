package de.jeske.restapiwithopenai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class RestapiwithopenaiApplication

fun main(args: Array<String>) {
    runApplication<RestapiwithopenaiApplication>(*args)
}