package example.com.database.tokens

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object Tokens : Table() {
    private val id = Tokens.varchar("id", 50)
    val login = Tokens.varchar("login", 25)
    val token = Tokens.varchar("token", 50)

    fun insertOrUpdate(tokenDto: TokensDTO){
        transaction {
            val isTokenExist = Tokens.select { login eq tokenDto.login }.singleOrNull()
            if(isTokenExist == null){
                Tokens.insert{
                    it[id] = tokenDto.id
                    it[login] = tokenDto.login
                    it[token] = tokenDto.token
                }
            }else{
                val newToken = UUID.randomUUID().toString()
                Tokens.update(where = {login eq tokenDto.login}){
                    it[token] = newToken
                }
            }
        }
    }

    fun checkToken(token: String): TokensDTO?{
        return try {
            transaction {
                Tokens.select { Tokens.token eq token }
                    .map { row ->
                        TokensDTO(
                            id = row[Tokens.id],
                            login = row[Tokens.login],
                            token = row[Tokens.token]
                        )
                    }
                    .singleOrNull()
            }
        } catch (e: Exception) {
            println("error: $e")
            null
        }
    }

}