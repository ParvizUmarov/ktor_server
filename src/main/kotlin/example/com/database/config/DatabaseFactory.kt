package example.com.database.config

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private const val dbUrl = "jdbc:postgresql://localhost:5432/ktor_db"
    private const val user = "parviz"
    private const val password = "qwerty"
    private const val driver = "org.postgresql.Driver"

    fun Application.initializeDb(){
        Database.connect(
            url =  dbUrl,
            driver = driver,
            user = user,
            password = password
        )
        /*
        Database.connect(
        "jdbc:postgresql://localhost:5432/ktor_db",
         driver = "org.postgresql.Driver",
         password = "qwerty",
         user = "parviz")
         */

    }

}