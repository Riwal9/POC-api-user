package user.model

import org.jetbrains.exposed.sql.Table
import java.util.*

object User : Table() {
    val id = uuid("id").primaryKey()
    val last_name = varchar("last_name", 50)
    val first_name = varchar("first_name", 50)
    val phoneNumber = varchar("phoneNumber", 10)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val gender = varchar("gender", 50)
    val birth_date = date("birth_date")
    val privateAccount = varchar("privateAccount", 50)
}

class UserObject(
    val id:String,
    var last_name: String,
    var first_name: String,
    var phoneNumber: String,
    var email: String,
    var password: String,
    var gender: String,
    var birth_date: Date,
    var privateAccount: String
)

