package com.security.togar.myapplication.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineContext = Dispatchers.IO,
    apiCall: suspend () -> T?
): T {
    return withContext(dispatcher) {
        try {
            apiCall() ?: throw Exception("Network null")
        } catch (error: Throwable) {
            throw error
        }
    }
}
