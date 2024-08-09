package example.com.features.todo

import example.com.database.todo.TodoDTO
import kotlinx.serialization.Serializable

@Serializable
data class TodoInsertReceive(
    val task: String,
)

@Serializable
data class TodoUpdateReceive(
    val id: String,
    val text: String
)

@Serializable
data class TodoUpdateIsDoneValueReceive(
    val id: String,
    val isDone: Boolean
)

@Serializable
data class TodoDeleteReceive(
    val id: String
)

@Serializable
data class TodoResponseRemote(
    val listOfTodo: List<TodoDTO>
)

@Serializable
data class TodoResponse(
    val id: String,
    val login: String,
    val task: String,
    val time: Long,
    val isDone: Boolean
)
