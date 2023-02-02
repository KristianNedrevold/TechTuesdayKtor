package no.hnikt.service

import no.hnikt.api.requests.FormRequest
import no.hnikt.repository.MiniFormRepository
import no.hnikt.repository.MiniPatientRepository
import no.hnikt.repository.MiniPatientRepositoryResult

sealed interface MiniFormServiceResult<out T> {
    data class Failure(val error: String) : MiniFormServiceResult<Nothing>
    data class Success<T>(val value: T) : MiniFormServiceResult<T>
}

interface MiniFormService {
    suspend fun createForm(formRequest: FormRequest) : MiniFormServiceResult<String>
    suspend fun readForm(formId: String): MiniFormServiceResult<String>
    suspend fun updateForm()
    suspend fun deleteForm()
}

fun miniFormService(
    miniFormRepository: MiniFormRepository,
    miniPatientRepository: MiniPatientRepository
) = object : MiniFormService {
    override suspend fun createForm(formRequest: FormRequest): MiniFormServiceResult<String> =
        when (val patient = miniPatientRepository.upsertPatient(formRequest.ssn)) {
            is MiniPatientRepositoryResult.Success -> {
                 miniFormRepository.createMiniForm(patient.value, formRequest.formData)
                MiniFormServiceResult.Success("You not dummy")
            }
            is MiniPatientRepositoryResult.Failure -> MiniFormServiceResult.Failure(patient.reason)
        }

    override suspend fun readForm(formId: String): MiniFormServiceResult<String> {
        TODO("Not yet implemented")
    }


    override suspend fun updateForm() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteForm() {
        TODO("Not yet implemented")
    }

}