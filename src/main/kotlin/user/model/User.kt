package user.model

import org.jetbrains.exposed.dao.UUIDTable

object User : UUIDTable() {
    val last_name = varchar("last_name", 50)
    val first_name = varchar("first_name", 50)
    val phoneNumber = varchar("phoneNumber", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val gender = varchar("gender", 50)
    val birth_date = varchar("birth_date", 50)
    val privateAccount = varchar("privateAccount", 50)
}

class UserObject(
    val uuid:String,
    val last_name: String,
    val first_name: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val gender: String,
    val birth_date: String,
    val privateAccount: String
)

