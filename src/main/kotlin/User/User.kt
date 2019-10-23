package User

import java.util.*

data class User(
    val uuid: UUID,
    var lastname: String,
    var firstname: String,
    var phoneNumber: String,
    var email: String,
    private var password: String,
    var genre: Char,
    var birthDate: Date,
    var login: String,
    var privateAccount: PrivateState
)

enum class PrivateState { PRIVATE, PUBLIC}
