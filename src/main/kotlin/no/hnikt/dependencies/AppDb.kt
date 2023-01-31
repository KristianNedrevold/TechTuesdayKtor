package no.hnikt.dependencies

import no.hnikt.domain.Ssn
import no.hnikt.repository.MiniFormEntity

object AppDb {

    fun getPatientDb() = mutableSetOf<Ssn>()
    fun getFormDb() = mutableListOf<MiniFormEntity>()
}