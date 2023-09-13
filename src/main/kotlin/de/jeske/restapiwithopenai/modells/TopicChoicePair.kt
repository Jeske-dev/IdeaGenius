package de.jeske.restapiwithopenai.modells

class TopicChoicePair (val topic: String, val choice: String) {
    override fun toString(): String {
        return "\"${topic}\": \"${choice}\","
    }
}