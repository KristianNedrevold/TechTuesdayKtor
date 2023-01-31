package no.hnikt

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import no.hnikt.dependencies.AppDb
import no.hnikt.dependencies.setupDependencies

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::setup)
        .start(wait = true)
}

fun Application.setup() {
    setupPlugins()
    val db = AppDb
    val dependencies = setupDependencies(db)
}

fun Application.setupPlugins() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            prettyPrint = true
        })
    }
}


