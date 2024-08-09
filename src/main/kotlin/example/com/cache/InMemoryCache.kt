package example.com.cache

import example.com.features.register.RegisterRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterRemote> = mutableListOf()
    val tokens: MutableList<TokenCache> = mutableListOf();
}