package no.hnikt.dependencies

import no.hnikt.repository.miniFormRepository
import no.hnikt.repository.miniPatientRepository
import no.hnikt.service.MiniFormService
import no.hnikt.service.miniFormService

interface Dependencies {
    val miniFormService: MiniFormService
}

fun setupDependencies(db: AppDb) = object : Dependencies {
    override val miniFormService: MiniFormService =
        miniFormService(miniFormRepository(db.getFormDb()), miniPatientRepository(db.getPatientDb()))
}