package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import org.bson.types.ObjectId

data class UserDTO(
    val id: String,
    val email: String?,
    val surname: String?,
    val firstname:String?,
    val processIds: List<String> = listOf()
) {
    constructor(user: User): this(
        user.id.toHexString(),
        user.email,
        user.surname,
        user.firstname,
        user.processIds.map { it.toHexString() }
    )
}