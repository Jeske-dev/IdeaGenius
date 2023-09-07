package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import org.bson.types.ObjectId

data class UserDTO(
    val id: ObjectId,
    val email: String?,
    val surname: String?,
    val firstname:String?,
    val processIds: List<String> = emptyList()
) {
    constructor(user: User): this(user.id, user.email, user.surname, user.firstname, user.processIds)
}