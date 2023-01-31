package no.hnikt.domain

import kotlinx.serialization.Serializable

@Serializable
data class MiniForm(
    val age: Age,
    val painLevel: PainLevel,
    val medicated: Medicated,
    val medicationTypes: List<String>?
)

@Serializable
@JvmInline
value class Age(val value: Int)

@Serializable
enum class PainLevel {
    NoPain, SlightPain, ModeratePain, SeverePain
}

@Serializable
enum class Medicated {
    None, One, Several
}
