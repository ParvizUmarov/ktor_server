package example.com

import example.com.database.config.DatabaseFactory.initializeDb
import example.com.features.login.configureLoginRouting
import example.com.features.register.configureRegisterRouting
import example.com.features.todo.configureTodoRouting
import example.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initializeDb()
    configureSerialization()
    configureLoginRouting()
    configureRegisterRouting()
    configureRouting()
    configureTodoRouting()
}
