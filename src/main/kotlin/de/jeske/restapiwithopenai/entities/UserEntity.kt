package de.jeske.restapiwithopenai.entities

import de.jeske.restapiwithopenai.modells.User
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserEntity(
    @Id
    val _id: ObjectId,
    val email: String,
    val surname: String,
    val firstname:String,
    val processIds: List<ObjectId> = listOf()
) {
    constructor(user: User): this(user.id, user.email, user.surname, user.firstname, user.processIds)

    fun toUser(): User = User(_id, email, surname, firstname, processIds)
}
