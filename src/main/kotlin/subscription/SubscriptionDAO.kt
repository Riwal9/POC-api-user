package subscription

import com.google.gson.GsonBuilder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import subscription.model.Subscription
import subscription.model.SubscriptionObject
import java.util.*
import com.google.gson.JsonParser
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


private const val username = utils.USERNAME // provide the username
private const val password = utils.PASSWORD // provide the corresponding password
private const val databaseHost = utils.DATABASE_HOST
private const val databasePort = utils.DATABASE_PORT
private const val databaseName = utils.DATABASE_NAME

class SubscriptionDAO() {

    init {
        Database.connect(
            url = "jdbc:postgresql://$databaseHost:$databasePort/$databaseName",
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
        transaction {
            SchemaUtils.create(Subscription)
        }
    }

    fun getSubscriptions(fromString: UUID): MutableList<SubscriptionObject> {
        lateinit var query: Query
        transaction {
            query = Subscription.select { (Subscription.fromUser eq fromString) }
            }
        if(getSubscriptionsFromQuery(query).isEmpty())
            throw NoSuchElementException("No users found")
        return getSubscriptionsFromQuery(query)
    }


    fun subscriptionExist(user: UUID, subscribed_to: UUID): Boolean {
        lateinit var query: Query
        transaction {
            query = Subscription.select { (Subscription.fromUser eq user) and (Subscription.subscribed_to eq subscribed_to)}
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
        var uuidToSubscribe = JsonParser().parse(JSONSubscribedTo).getAsJsonObject().get("uuidsubscription").asString
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