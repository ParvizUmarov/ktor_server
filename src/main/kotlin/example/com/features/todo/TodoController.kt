package example.com.features.todo

import example.com.database.todo.Todo
import example.com.database.tokens.Tokens
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TodoController {

    suspend fun updateDoneValue(call : ApplicationCall){
        val token = call.request.headers["token"]
        val checkToken = token?.let { Tokens.checkToken(it) }
        if(checkToken != null){
            try {
                val receive = call.receive<TodoUpdateIsDoneValueReceive>()
                Todo.changeTodoCheckBoxValue(receive)
                call.respond(HttpStatusCode.OK)
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest)
            }
        }else{
            call.respond(HttpStatusCode.BadRequest, "You must authorized")
        }
    }

    suspend fun deleteTask(call: ApplicationCall){
        val token = call.request.headers["token"]
        val checkToken = token?.let { Tokens.checkToken(it) }
        if(checkToken != null){
            val receive = call.receive<TodoDeleteReceive>()
            val delete = Todo.deleteTodo(receive.id)

            if(delete){
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(HttpStatusCode.Conflict)
            }
        }else{
            call.respond(HttpStatusCode.BadRequest, "You must authorized")
        }
    }

    suspend fun getAllTodo(call: ApplicationCall) {
        val token = call.request.headers["token"]
        val checkToken = token?.let { Tokens.checkToken(it) }
        if(checkToken != null){
            val allTodo = Todo.readAll(checkToken.login)
            call.respond(TodoResponseRemote(listOfTodo= allTodo))

        }else{
            call.respond(HttpStatusCode.BadRequest, "You must authorized")
        }
    }

    suspend fun createTask(call: ApplicationCall){
        val token = call.request.headers["token"]
        val checkToken = token?.let { Tokens.checkToken(it) }
        if(checkToken != null){
            val receive = call.receive<TodoInsertReceive>()
            Todo.insert(todoReceive =  receive, tokensDTO = checkToken)
            call.respond(HttpStatusCode.OK)
        }else{
            call.respond(HttpStatusCode.BadRequest, "You must authorized")
        }
    }

    suspend fun updateTask(call: ApplicationCall) {
        val token = call.request.headers["token"]
        val checkToken = token?.let { Tokens.checkToken(it) }
        if(checkToken != null){
            val receive = call.receive<TodoUpdateReceive>()
            Todo.updateTodo(receive)
            call.respond(HttpStatusCode.OK)
        }else{
            call.respond(HttpStatusCode.BadRequest, "You must authorized")
        }
    }
}