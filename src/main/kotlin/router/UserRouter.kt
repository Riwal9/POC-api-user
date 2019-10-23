package router

import io.javalin.ApiBuilder.*
import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("Not found") }
    }.start()

    app.routes {

        get("/users") { ctx ->
            ctx.json("Get all users")
            }

        get("/users/:uuid") { ctx ->
            ctx.json("Get one specific user")
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
