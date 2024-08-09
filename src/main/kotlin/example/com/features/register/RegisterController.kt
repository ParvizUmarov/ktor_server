package example.com.features.register

import example.com.database.tokens.Tokens
import example.com.database.tokens.TokensDTO
import example.com.database.users.UserDTO
import example.com.database.users.Users
import example.com.utils.isEmailValid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val receive = call.receive<RegisterRemote>()

        if(!receive.email.isEmailValid()){
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDto = Users.fetchUser(receive.login)

        if (userDto != null) {
            call.respond(HttpStatusCode.Conflict, "User already exist")
        } else {
            val token = UUID.randomUUID().toString()

            Users.insert(UserDTO(
                login = receive.login,
                password = receive.password,
                email = receive.email,
                username = ""
            ))

            Tokens.insertOrUpdate(TokensDTO(
                id = UUID.randomUUID().toString(),
                login = receive.login,
                token = token
                ))
            call.respond(RegisterResponseRemote(token = token))
        }

    }

}