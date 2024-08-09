package example.com.features.todo

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTodoRouting() {
    val todoController = TodoController()
    routing {
        get("/todo") {
            todoController.getAllTodo(call)
        }
        post ("/todo/create-task"){
            todoController.createTask(call)
        }
        delete("/todo/delete-task") {
            todoController.deleteTask(call)
        }
        patch ("/todo/update-task"){
            todoController.updateTask(call)
        }
        patch ("/todo/check-task"){
            todoController.updateDoneValue(call)
        }



    }
}