package user

import com.google.gson.GsonBuilder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import user.model.User
import user.model.UserObject
import java.util.*

private const val username = utils.USERNAME // provide the username
private const val password = utils.PASSWORD // provide the corresponding password
private const val databaseHost = utils.DATABASE_HOST
private const val databasePort = utils.DATABASE_PORT
private const val databaseName = utils.DATABASE_NAME

class UserDao() {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    init {
        Database.connect(
            url = "jdbc:postgresql://$databaseHost:$databasePort/$databaseName",
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
    }

    // Handled by keycloak ?
    fun create() {
        transaction {
            SchemaUtils.create(User)
            User.insert {
                it[email] = "Marcel"
                it[password] = "Pagnol"
            }
        }
    }

    fun getUsers(): String {
        lateinit var query:Query
        transaction {
            query = User.selectAll()
        }
        return gson.toJson(getUsersFromQuery(query))
    }

    fun getUser(uuid: UUID): String {
        lateinit var query:Query
        transaction {
            query = User.select { User.id eq uuid }
        }
        return gson.toJson(getUsersFromQuery(query))
    }


    private fun getUsersFromQuery(query: Query): MutableList<UserObject> {
        val users = mutableListOf<UserObject>()
        transaction {
            query.forEach {
                var user = UserObject(
                    it.data[0].toString(),
                    it.data[1].toString(),
                    it.data[2].toString(),
                    it.data[3].toString(),
                    it.data[4].toString(),
                    it.data[5].toString(),
                    it.data[6].toString(),
                    it.data[7].toString(),
                    it.data[8].toString()
                )
                users.add(user)
            }
        }
        return users
    }
}