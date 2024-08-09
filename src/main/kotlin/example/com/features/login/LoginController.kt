package example.com.features.login

import example.com.database.tokens.Tokens
import example.com.database.tokens.TokensDTO
import example.com.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin(){
        val receive = call.receive<LoginReceiveRemote>()

        val user = Users.fetchUser(receive.login)

        if(user == null){
            call.respond(HttpStatusCode.Conflict, "User doesn`t exist ")
        }else{
            if(user.password == receive.password){
                val token = UUID.randomUUID().toString();
                Tokens.insertOrUpdate(
                    TokensDTO(
                        id = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            }else{
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }

}