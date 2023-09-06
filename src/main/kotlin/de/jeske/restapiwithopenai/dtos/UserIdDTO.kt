package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.entities.UserEntity
import de.jeske.restapiwithopenai.modells.User
import org.bson.types.ObjectId

data class UserIdDTO(val userId: ObjectId) {
    constructor(user: User): this(user.id)
}
