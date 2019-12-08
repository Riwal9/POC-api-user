package subscription

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import subscription.model.Subscription
import subscription.model.SubscriptionObject
import java.util.*
import com.google.gson.JsonParser
import utils.*

class SubscriptionDAO() {

    init {
        Database.connect(
            url = "jdbc:postgresql://$DATABASE_HOST:$DATABASE_PORT/$DATABASE_NAME",
            driver = "org.postgresql.Driver",
            user = USERNAME,
            password = PASSWORD
        )
        transaction {
            SchemaUtils.create(Subscription)
        }
    }

    fun getSubscriptions(fromUser: UUID): MutableList<SubscriptionObject> {
        lateinit var query: Query
        transaction {
            query = Subscription.select { (Subscription.fromUser eq fromUser) }
            }
        if(getSubscriptionsFromQuery(query).isEmpty())
            throw NoSuchElementException("No users found")
        return getSubscriptionsFromQuery(query)
    }


    fun subscriptionExist(fromUser: UUID, subscribed_to: UUID): Boolean {
        lateinit var query: Query
        transaction {
            query = Subscription.select { (Subscription.fromUser eq fromUser) and (Subscription.subscribed_to eq subscribed_to)}
        }
        return getSubscriptionsFromQuery(query).isNotEmpty()
    }

    fun subscribe(uuidFrom: UUID, uuidToSubscribe: UUID): String {
        transaction {
            Subscription.insert {
                it[fromUser] = uuidFrom
                it[subscribed_to] = uuidToSubscribe
            }
        }
        return "User$uuidFrom subscribed to User$uuidToSubscribe"
    }

    fun unsubscribe(uuidFrom: UUID, uuidToSubscribe: UUID): String {
        transaction {
            Subscription.deleteWhere {
                (Subscription.fromUser eq uuidFrom) and (Subscription.subscribed_to eq uuidToSubscribe)
            }
        }
        return "User$uuidFrom subscribed to User$uuidToSubscribe"
    }

    fun unsubscribe(uuidFrom: UUID, JSONSubscribedTo: String): String {
        var uuidToSubscribe = JsonParser().parse(JSONSubscribedTo).asJsonObject.get("uuidsubscription").asString
        return unsubscribe(uuidFrom, UUID.fromString(uuidToSubscribe))
    }

    private fun getSubscriptionsFromQuery(query: Query): MutableList<SubscriptionObject> {
        val subscriptions = mutableListOf<SubscriptionObject>()
        transaction {
            query.forEach {
                var subscription = SubscriptionObject(
                    UUID.fromString(it.data[1].toString()),
                    UUID.fromString(it.data[2].toString())
                )
                subscriptions.add(subscription)
            }
        }
        return subscriptions
    }
}