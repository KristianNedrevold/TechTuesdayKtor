package no.hnikt.domain

import kotlinx.serialization.Serializable

@Serializable
data class MiniPatient(
    val ssn: Ssn,
    val pid: Int,
)

@Serializable
@JvmInline
value class Ssn(val value: String)
