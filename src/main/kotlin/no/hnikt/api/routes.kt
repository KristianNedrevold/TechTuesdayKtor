package no.hnikt.api

import io.ktor.server.application.*
import no.hnikt.api.routes.miniFormRoutes
import no.hnikt.service.MiniFormService

fun Application.setupRoutes(miniFormService: MiniFormService) {
    miniFormRoutes(miniFormService)
}