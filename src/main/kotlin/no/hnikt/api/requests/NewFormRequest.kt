package no.hnikt.api.requests

import kotlinx.serialization.Serializable
import no.hnikt.domain.MiniForm
import no.hnikt.domain.Ssn

@Serializable
data class FormRequest(
    val ssn: Ssn,
    val formData: MiniForm
)
