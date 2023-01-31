package no.hnikt.api.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import no.hnikt.api.requests.FormRequest
import no.hnikt.service.MiniFormService

fun Application.miniFormRoutes() {

}

fun Route.createForm(miniFormService: MiniFormService): Route = post {
        val request: FormRequest = call.receive()
        val requestCaught = receiveCatching<FormRequest>()
    }

fun Route.readForm(): Route = TODO()

fun Route.updateForm() : Route = TODO()

fun Route.deleteForm() : Route = TODO()

sealed interface RequestConversion<out T> {
     data class Failure(val reason: String) : RequestConversion<Nothing>
    data class Success<T>(val value: T) : RequestConversion<T>
}

suspend inline fun <reified T: Any>PipelineContext<Unit, ApplicationCall>.receiveCatching(): RequestConversion <T> = try {
    RequestConversion.Success(call.receive())
} catch (e: Exception) {RequestConversion.Failure("Could not convert request to ${T::class.java.canonicalName}") }