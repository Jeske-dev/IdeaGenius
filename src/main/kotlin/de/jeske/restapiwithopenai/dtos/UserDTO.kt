package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.entities.UserEntity
import org.bson.types.ObjectId

data class UserDTO(
    val id: ObjectId,
    val email: String,
    val surname: String,
    val firstname:String,
    val processIds: List<String> = emptyList()
) {
    constructor(user: UserEntity): this(user.id, user.email, user.surname, user.firstname, user.processIds)
}