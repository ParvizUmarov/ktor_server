package example.com.database.users

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val login = Users.varchar("login", 50)
    private val username = Users.varchar("username", 25)
    private val email = Users.varchar("email", 25)
    private val password = Users.varchar("password", 25)

    fun insert(userDto: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDto.login
                it[username] = userDto.username
                it[email] = userDto.email ?: ""
                it[password] = userDto.password
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select(Users.login.eq(login)).single()
                UserDTO(
                    login = userModel[Users.login],
                    username = userModel[Users.username],
                    email = userModel[Users.email],
                    password = userModel[Users.password]
                )
            }
        } catch (e: Exception) {
            println("error: $e")
            null
        }
    }

}