package no.hnikt.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

suspend fun <A>blockingIO(f: () -> A) = withContext(Dispatchers.IO) {f()}

suspend fun <A>withMutex(mutex: Mutex, f: () -> A) = withContext(Dispatchers.IO) {  mutex.withLock{ f() } }