package de.jeske.restapiwithopenai.modells

class QuestionChoicePair (val question: String, val choice: String) {
    override fun toString(): String {
        return "\"${question}\": \"${choice}\","
    }
}