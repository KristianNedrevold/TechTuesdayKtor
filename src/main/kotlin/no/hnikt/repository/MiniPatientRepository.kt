package no.hnikt.repository

import kotlinx.coroutines.sync.Mutex
import no.hnikt.domain.MiniPatient
import no.hnikt.domain.Ssn
import no.hnikt.utils.blockingIO
import no.hnikt.utils.withMutex


sealed interface MiniPatientRepositoryResult<out T> {
    data class Failure(val reason: String): MiniPatientRepositoryResult<Nothing>
    data class Success<T>(val value: T): MiniPatientRepositoryResult<T>
}

interface MiniPatientRepository {
    suspend fun createMiniPatient(ssn: Ssn): MiniPatientRepositoryResult<Int>
    suspend fun readPatient(ssn: Ssn): MiniPatientRepositoryResult<MiniPatient>
    suspend fun deletePatient(ssn: Ssn): MiniPatientRepositoryResult<Unit>
    suspend fun upsertPatient(ssn: Ssn): MiniPatientRepositoryResult<MiniPatient>
}

fun miniPatientRepository(db: MutableSet<Ssn>) = object : MiniPatientRepository {
    val repoMutex = Mutex()

    override suspend fun createMiniPatient(ssn: Ssn): MiniPatientRepositoryResult<Int> = withMutex(repoMutex) {
        if (!db.contains(ssn)) {
            db.add(ssn)
            MiniPatientRepositoryResult.Success(db.indexOf(ssn))
        } else MiniPatientRepositoryResult.Failure("Already exists")
    }

    override suspend fun readPatient(ssn: Ssn): MiniPatientRepositoryResult<MiniPatient> = blockingIO {
        if (db.contains(ssn)) MiniPatientRepositoryResult.Success(MiniPatient(ssn, db.indexOf(ssn)))
        else MiniPatientRepositoryResult.Failure("No such patient")
    }

    override suspend fun deletePatient(ssn: Ssn): MiniPatientRepositoryResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertPatient(ssn: Ssn): MiniPatientRepositoryResult<MiniPatient> = withMutex(repoMutex) {
       if (!db.contains(ssn)) {
           db.add(ssn)
           MiniPatientRepositoryResult.Success(MiniPatient(ssn, db.indexOf(ssn)))
       } else MiniPatientRepositoryResult.Failure("You dummy")
    }
}