package router

import io.javalin.ApiBuilder.*
import io.javalin.Javalin
import user.UserDao
import user.model.User
import java.util.*

fun main(args: Array<String>) {
    val userDAO = UserDao()
    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("Not found") }
    }.start()

    app.routes {

        get("/users") { ctx ->
            ctx.result(
                userDAO.getUsers()
            )
            ctx.status(200)
        }

        get("/users/:uuid") { ctx ->
            ctx.result(
                userDAO.getUser(UUID.fromString(ctx.param("uuid")))
            )
            ctx.status(200)
        }

        //TO REMOVE if handled by keycloak
        get("/create") { ctx ->
            ctx.json(userDAO.create())
        }

        put("/users") { ctx ->
            userDAO.updateUser(ctx.body())
            ctx.status(204)
        }

        delete("/users/:uuid") { ctx ->
            ctx.result(
                userDAO.deleteUser(UUID.fromString(ctx.param("uuid")))
            )
            ctx.status(204)
        }

        post("/users/:uuid/subscribe") { ctx ->
            ctx.json("Subscribe to user")
        }

        post("/users/:uuid/unsubscribe") { ctx ->
            ctx.json("Unsubscribe to user")
        }
    }
}
