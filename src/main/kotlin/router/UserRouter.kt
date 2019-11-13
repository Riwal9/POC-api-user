package router

import io.javalin.ApiBuilder.*
import io.javalin.Javalin
import user.UserDao
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
        }

        get("/users/:uuid") { ctx ->
            ctx.result(userDAO.getUser(UUID.fromString(ctx.param("uuid"))))
        }

        get("/create") { ctx ->
            ctx.json(userDAO.create())
        }

        put("/users/:uuid") { ctx ->
            ctx.json("Update user")
        }

        delete("/users/:uuid") { ctx ->
            ctx.json("Delete user")
        }

        post("/users/:uuid/subscribe") { ctx ->
            ctx.json("Subscribe to user")
        }

        post("/users/:uuid/unsubscribe") { ctx ->
            ctx.json("Unsubscribe to user")
        }
    }
}
