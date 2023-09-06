package de.jeske.restapiwithopenai.dtos

import de.jeske.restapiwithopenai.entities.UserEntity
import org.bson.types.ObjectId

data class UserIdDTO(val userId: ObjectId) {
    constructor(user: UserEntity): this(user.id)
}
