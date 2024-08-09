package example.com.database.todo

import kotlinx.serialization.Serializable

@Serializable
data class TodoDTO(
    val id: String,
    val login: String,
    val task: String,
    val time: String,
    val isDone: Boolean
    )