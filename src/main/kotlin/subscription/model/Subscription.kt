package subscription.model

import org.jetbrains.exposed.dao.UUIDTable
import org.jetbrains.exposed.sql.Table
import java.util.*
import user.model.User
import user.model.User.primaryKey

object Subscription : UUIDTable() {
    val fromUser = (uuid("fromUser") references User.id)
    val subscribed_to = (uuid("subscribed_to") references User.id)
}


class SubscriptionObject(
    val user: UUID,
    val subscribed_to: UUID
)