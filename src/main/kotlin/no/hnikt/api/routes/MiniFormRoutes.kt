package no.hnikt.api.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import no.hnikt.api.requests.FormRequest
import no.hnikt.api.requests.GetFormRequest
import no.hnikt.service.MiniFormService
import no.hnikt.service.MiniFormServiceResult

fun Application.miniFormRoutes(miniFormService: MiniFormService) {
    routing {
        route("/mini") {
            createForm(miniFormService)
            readForm(miniFormService)
        }
    }
}

fun Route.createForm(miniFormService: MiniFormService): Route = post {
        val formRequest: FormRequest = call.receive()
        when (val createResult = miniFormService.createForm(formRequest)) {
            is MiniFormServiceResult.Success -> call.respondText("Good job")
            is MiniFormServiceResult.Failure -> call.respond(HttpStatusCode.BadRequest, createResult.error)
        }
    }

fun Route.readForm(miniFormService: MiniFormService): Route = get {
    val formId = call.receive<GetFormRequest>()
    val formIdCaught = receiveCatching<GetFormRequest>()
    when (val result = miniFormService.readForm(formId.formId)) {
        is MiniFormServiceResult.Failure -> call.respond(HttpStatusCode.BadRequest, result.error)
        is MiniFormServiceResult.Success -> call.respond(HttpStatusCode.OK, result.value)
    }
}


fun Route.updateForm() : Route = TODO()

fun Route.deleteForm() : Route = TODO()

sealed interface RequestConversion<out T> {
     data class Failure(val reason: String) : RequestConversion<Nothing>
    data class Success<T>(val value: T) : RequestConversion<T>
}

private suspend inline fun <reified T: Any>PipelineContext<Unit, ApplicationCall>.receiveCatching(): RequestConversion <T> = try {
    RequestConversion.Success(call.receive())
} catch (e: Exception) {RequestConversion.Failure("Could not convert request to ${T::class.java.canonicalName}") }