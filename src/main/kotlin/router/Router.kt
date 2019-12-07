package router

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.javalin.ApiBuilder.*
import io.javalin.Javalin
import subscription.SubscriptionDAO
import user.UserDao
import java.util.*

class Router {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    init {
        val userDAO = UserDao()
        val subscriptionDAO = SubscriptionDAO()
        val app = Javalin.create().apply {
            port(7000)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("Not found") }
        }.start()

        app.routes {

            get("/users") { ctx ->
                ctx.result(
                    gson.toJson(userDAO.getUsers())
                )
                ctx.status(200)
            }

            get("/users/:uuid") { ctx ->
                ctx.result(
                    gson.toJson(userDAO.getUser((UUID.fromString(ctx.param("uuid")))))
                )
                ctx.status(200)
            }

            put("/users") { ctx ->
                userDAO.updateUserFromJson(ctx.body())
                ctx.status(204)
            }

            delete("/users/:uuid") { ctx ->
                ctx.json(
                    userDAO.deleteUser(UUID.fromString(ctx.param("uuid")))
                )
                ctx.status(204)
            }

            get("/users/:uuid/subscriptions") { ctx ->
                ctx.result(
                    gson.toJson(subscriptionDAO.getSubscriptions(UUID.fromString(ctx.param("uuid"))))
                )
                ctx.status(200)
            }

            get("/users/:uuid/subscriptions/:uuidSubscribedUser") { ctx ->
                ctx.result(
                    gson.toJson(subscriptionDAO.subscriptionExist(UUID.fromString(ctx.param("uuid")), UUID.fromString(ctx.param("uuidSubscribedUser"))))
                )
                ctx.status(200)
            }


            post("/users/:uuid/subscribe") { ctx ->
                ctx.json("Subscribe to user")
                var uuidToSubscribe = JsonParser().parse( ctx.body()).getAsJsonObject().get("uuidsubscription").asString
                subscriptionDAO.subscribe(UUID.fromString(ctx.param("uuid")), UUID.fromString(uuidToSubscribe))
                ctx.status(204)
            }

            post("/users/:uuid/unsubscribe") { ctx ->
                ctx.json("Unsubscribe to user")
                subscriptionDAO.unsubscribe(UUID.fromString(ctx.param("uuid")), ctx.body())
                ctx.status(204)
            }
        }
    }
}
