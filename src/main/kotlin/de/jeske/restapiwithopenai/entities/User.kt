package de.jeske.restapiwithopenai.entities

data class User(val _id: String, val email: String, val surname: String, val firstname:String, val processIds: List<String>)
