package no.hnikt.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class NewFormResponse(
    val stored: Boolean
)
