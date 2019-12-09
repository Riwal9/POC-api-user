package user

import com.google.gson.GsonBuilder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import user.model.User
import user.model.UserObject
import utils.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.NoSuchElementException

class UserDao() {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private var format = SimpleDateFormat("dd-MM-yyy")

    init {
        Database.connect(
            url = "jdbc:postgresql://$DATABASE_HOST:$DATABASE_PORT/$DATABASE_NAME",
            driver = "org.postgresql.Driver",
            user = USERNAME,
            password = PASSWORD
        )
        transaction {
            SchemaUtils.create(User)
        }
    }

    // Handled by keycloak ?
    //Test method to remove
    fun createUser(user: UserObject) {
        transaction {
            User.insert {
                it[id] = UUID.fromString(user.id)
                it[last_name] = user.last_name
                it[first_name] = user.first_name
                it[phoneNumber] = user.phoneNumber
                it[email] = user.email
                it[password] = user.password
                it[gender] = user.gender
                it[birth_date] = DateTime(user.birth_date)
                it[privateAccount] = user.privateAccount
            }
        }
    }

    fun getUsers(): MutableList<UserObject> {
        lateinit var query: Query
        transaction {
            query = User.selectAll()
        }
        return getUsersFromQuery(query)
    }

    fun getUser(uuid: UUID): UserObject {
        lateinit var query: Query
        transaction {
            query = User.select { User.id eq uuid }
        }
        if(getUsersFromQuery(query).isEmpty())
            throw NoSuchElementException("No user found")
        else
            return getUsersFromQuery(query).first()
    }


    fun updateUserFromJson(userJson: String): String {
        var user: UserObject = gson.fromJson(userJson, UserObject::class.java)
        updateUser(user)
        return "User ${user.id} updated"
    }

    fun updateUser(user: UserObject) {
        transaction {
            User.update({ User.id eq UUID.fromString(user.id) }) {
                it[last_name] = user.last_name
                it[first_name] = user.first_name
                it[phoneNumber] = user.phoneNumber
                it[email] = user.email
                it[password] = user.password
                it[gender] = user.gender
                it[birth_date] = DateTime(user.birth_date)
                it[privateAccount] = user.privateAccount
            }
        }
    }


    private fun getUsersFromQuery(query: Query): MutableList<UserObject> {
        val users = mutableListOf<UserObject>()
        transaction {
            query.forEach {
                var user = associateParams(it)
                users.add(user)
            }
        }
        return users
    }

    private fun associateParams(rr: ResultRow): UserObject{
        return UserObject(
            id = rr.data[0].toString(),
            last_name = rr.data[1].toString(),
            first_name = rr.data[2].toString(),
            phoneNumber = rr.data[3].toString(),
            email = rr.data[4].toString(),
            password = rr.data[5].toString(),
            gender = rr.data[6].toString(),
            birth_date = format.parse(rr.data[7].toString()),
            privateAccount = rr.data[8].toString()
        )
    }


    fun deleteUser(uuid: UUID): Int {
        var response = 0
        transaction {
            response = User.deleteWhere { User.id eq uuid }
        }
        return response
    }
}