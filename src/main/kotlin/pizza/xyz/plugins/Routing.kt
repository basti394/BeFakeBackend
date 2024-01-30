package pizza.xyz.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import pizza.xyz.dto.App
import pizza.xyz.dto.AppCreated
import java.io.File
import java.util.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        post("/upload") {

            val multipart = call.receiveMultipart()
            var app: App? = null
            var fileBytes: ByteArray? = null

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == "app") {
                            app = Json.decodeFromString(App.serializer(), part.value)
                        }
                    }
                    is PartData.FileItem -> {
                        fileBytes = part.streamProvider().readBytes()
                    }
                    else -> { }
                }
                part.dispose()
            }

            app?.let { a -> fileBytes?.let { fileBytes ->
                val appName = a.name + if (a.platform == "android") ".apk" else ".ipa"
                val dir = File("apps/${a.version}/${a.platform}/")
                dir.mkdirs()
                val file = File(dir, appName)
                file.createNewFile()
                file.writeBytes(fileBytes)
                call.respondText("File is uploaded")
            }} ?: call.respond(HttpStatusCode.BadRequest, "Missing file or app data")
        }
        get("/apps/newest") {
            call.respondText("Not implemented yet", status = HttpStatusCode.NotImplemented)
        }
        staticFiles("/apps", dir = File("apps"))
    }
}
