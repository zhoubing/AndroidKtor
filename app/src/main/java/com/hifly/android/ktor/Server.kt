package com.hifly.android.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay

class Server {
    var session: WebSocketServerSession? = null

    fun start() {
        embeddedServer(Netty, 8081) {
            install(WebSockets) {
            }

            routing {
                get("/") {
                    call.respondText("Hello Ktor", ContentType.Text.Html)
                }
                get("/json") {
                    call.respondText("""{"name" : "zhoubing"}""", ContentType.Application.Json)
                }

                webSocket("/echo") {
                    println("websocket echo")
                    delay(5000)
                    send("msg from ktor server")
                    session = this
                }
            }
//        }.start(wait = true)
        }.start()
        //https://github.com/netty/netty/issues/10682
    }

    suspend fun send(msg: String) {
        session?.send(msg)
    }
}