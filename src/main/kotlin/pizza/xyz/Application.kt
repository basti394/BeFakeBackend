package pizza.xyz

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import pizza.xyz.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureSerialization()
    configureRouting()
}
