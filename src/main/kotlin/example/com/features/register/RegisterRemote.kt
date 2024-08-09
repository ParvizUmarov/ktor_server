package example.com.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRemote(
    val login: String,
    val email: String,
    var password: String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)
