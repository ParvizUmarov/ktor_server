package example.com.database.todo

import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime
import example.com.database.tokens.TokensDTO
import example.com.features.todo.TodoInsertReceive
import example.com.features.todo.TodoUpdateIsDoneValueReceive
import example.com.features.todo.TodoUpdateReceive
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

object Todo : Table() {
    private val id = Todo.varchar("id", 50)
    private val login = Todo.varchar("login", 25)
    private val task = Todo.varchar("task", 150)
    private val time = Todo.varchar("time", 100)
    private val isDone = Todo.bool("is_done")

    fun deleteTodo(id: String): Boolean{
        return try {
            transaction {
                Todo.deleteWhere {
                    Todo.id eq id
                }
              true
            }
        }catch (e: Exception){
            false
        }
    }

    fun changeTodoCheckBoxValue(todoReceive: TodoUpdateIsDoneValueReceive){
        transaction {
            val now = LocalDateTime.now()
            Todo.update(where = { Todo.id eq todoReceive.id}){
                it[time] = now.toString()
                it[isDone] = todoReceive.isDone
            }
        }
    }

    fun updateTodo(todoReceive: TodoUpdateReceive){
        transaction {
            val now = LocalDateTime.now()
            Todo.update({ Todo.id eq todoReceive.id}){
                it[time] = now.toString()
                it[task] = todoReceive.text
            }
        }
    }

    fun insert(todoReceive: TodoInsertReceive, tokensDTO: TokensDTO) {
        transaction {
            val taskID = UUID.randomUUID().toString()
            val now = LocalDateTime.now()

            Todo.insert {
                it[id] = taskID
                it[login] = tokensDTO.login
                it[task] = todoReceive.task
                it[time] = now.toString()
                it[isDone] = false
            }
        }
    }

    fun readAll(login: String): List<TodoDTO> {
        return try {
            transaction {
                Todo.selectAll().where { Todo.login eq login }.toList().map {
                    TodoDTO(
                        id = it[Todo.id],
                        login = it[Todo.login],
                        task = it[task],
                        time = it[time],
                        isDone = it[isDone]
                    )
                }
            }
        } catch (e: Exception) {
            println("Error to get all todos: $e")
            emptyList()
        }
    }

}