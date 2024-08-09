package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class DefaultResponse(
    val text: String
)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(DefaultResponse("Hello Ktor!"))
        }
    }
}
