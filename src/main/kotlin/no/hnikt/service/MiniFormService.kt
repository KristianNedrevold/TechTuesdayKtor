package no.hnikt.service

import no.hnikt.repository.MiniFormRepository
import no.hnikt.repository.MiniPatientRepository

interface MiniFormService {
    suspend fun createForm()
    suspend fun readForm()
    suspend fun updateForm()
    suspend fun deleteForm()
}

fun miniFormService(
    miniFormRepository: MiniFormRepository,
    miniPatientRepository: MiniPatientRepository
) = object : MiniFormService {

}