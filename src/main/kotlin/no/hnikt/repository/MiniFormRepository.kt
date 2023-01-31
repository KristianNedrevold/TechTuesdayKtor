package no.hnikt.repository

import kotlinx.coroutines.sync.Mutex
import no.hnikt.domain.MiniForm
import no.hnikt.domain.MiniPatient
import no.hnikt.domain.Ssn
import no.hnikt.utils.blockingIO
import no.hnikt.utils.withMutex
import java.time.Instant
import java.time.LocalDateTime

data class MiniFormEntity(
    val miniFormPid: Int,
    val miniForm: MiniForm,
    val miniPatient: MiniPatient,
    val created: LocalDateTime
)

sealed interface MiniFormRepositoryResult<out T> {
    data class Failure(val reason: String) : MiniFormRepositoryResult<Nothing>
    data class Success<T>(val value: T) : MiniFormRepositoryResult<T>
}

interface MiniFormRepository {
    suspend fun createMiniForm(miniPatient: MiniPatient, miniForm: MiniForm): MiniFormRepositoryResult<Int>
    suspend fun readMiniForms(ssn: Ssn): MiniFormRepositoryResult<List<MiniFormEntity>>
    suspend fun readMiniForm(formId: Int): MiniFormRepositoryResult<MiniFormEntity>
    suspend fun updateMiniForm(miniFormId: Long, miniForm: MiniForm): MiniFormRepositoryResult<MiniFormEntity>
    suspend fun deleteMiniForm(miniFormId: Long): MiniFormRepositoryResult<Nothing>
}

fun miniFormRepository(db : MutableList<MiniFormEntity>) = object : MiniFormRepository {
    val repoMutex = Mutex()

    override suspend fun createMiniForm(miniPatient: MiniPatient, miniForm: MiniForm): MiniFormRepositoryResult<Int> = withMutex(repoMutex) {
        db.add(MiniFormEntity(db.size, miniForm, miniPatient, LocalDateTime.from(Instant.now())))
        MiniFormRepositoryResult.Success(db.size)
    }

    override suspend fun readMiniForms(ssn: Ssn): MiniFormRepositoryResult<List<MiniFormEntity>> = blockingIO {
        when {
            db.isEmpty() -> MiniFormRepositoryResult.Failure("No forms registered")
            else -> {
                val forms = db.filter { it.miniPatient.ssn == ssn }
                if (forms.isEmpty()) MiniFormRepositoryResult.Failure("No forms registered for patient")
                else MiniFormRepositoryResult.Success(forms)
            }
        }
    }

    override suspend fun readMiniForm(formId: Int): MiniFormRepositoryResult<MiniFormEntity> = blockingIO {
        try {
            MiniFormRepositoryResult.Success(db[formId])
        } catch (e: Exception) { MiniFormRepositoryResult.Failure("No such element")}
    }

    override suspend fun updateMiniForm(miniFormId: Long, miniForm: MiniForm): MiniFormRepositoryResult<MiniFormEntity> = withMutex(repoMutex) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMiniForm(miniFormId: Long): MiniFormRepositoryResult<Nothing> = withMutex(repoMutex) {
        TODO("Not yet implemented")
    }

}