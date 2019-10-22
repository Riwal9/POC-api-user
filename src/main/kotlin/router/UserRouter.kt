package router

import io.javalin.ApiBuilder.*
import io.javalin.Javalin

fun main(args: Array<String>) {

    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("coucou") }
    }.start()

    app.routes {

        get("/users") { ctx ->
            ctx.status(501)
        }

        get("/users/:uid") { ctx ->
            ctx.status(501)
        }

        put("/users/:uid") { ctx ->
            ctx.status(501)
        }

        delete("/users/:uid") { ctx ->
            ctx.status(501)
        }

        post("/users/:uid/subscribe") { ctx ->
            ctx.status(501)
        }

        post("/users/:uid/unsubscribe") { ctx ->
            ctx.status(501)
        }

    }
}
