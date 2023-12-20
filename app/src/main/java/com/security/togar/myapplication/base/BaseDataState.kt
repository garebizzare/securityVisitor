package com.security.togar.myapplication.base

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>
    data class Error(val exception: Throwable? = null) : DataState<Nothing>
    object Loading : DataState<Nothing>
    object Empty : DataState<Nothing>
    data class ErrorNetwork(val errorConnection: IOException? = null) : DataState<Nothing>
    data class ErrorHttpException(val errorCode: HttpException? = null) : DataState<Nothing>
}

suspend fun <T> MutableStateFlow<DataState<T>>.asMutableStateFlow(
    action: suspend () -> T
) {
    this.update { DataState.Loading }
    try {
        val data = action()
        if (data != null) {
            this.update { DataState.Success(data) }
        } else {
            this.update { DataState.Empty }
        }

    } catch (error: Throwable) {
        when (error) {
            is HttpException -> {
                this.update { DataState.ErrorHttpException(error) }
            }

            is IOException -> {
                this.update { DataState.ErrorNetwork(error) }
            }

            else -> {
                this.update { DataState.Error(error) }
            }
        }
    }
}

fun <T> DataState<T>.stateUI(
    viewLoading: View? = null,
    viewError: View? = null,
    viewEmpty: View? = null,
    viewErrorInternet: View? = null,
    viewErrorHttpException: View? = null,
    viewSuccess: List<View>? = null
) {
    viewLoading?.let { view ->
        view.isVisible = this is DataState.Loading
    }
    viewError?.let { view ->
        view.isVisible = this is DataState.Error
    }
    viewErrorInternet?.let { view ->
        view.isVisible = this is DataState.ErrorNetwork
    }
    viewEmpty?.let { view ->
        view.isVisible = this is DataState.Empty
    }
    viewErrorHttpException?.let { view ->
        view.isVisible = this is DataState.ErrorHttpException
    }
    viewSuccess?.let { view ->
        view.forEach { v ->
            v.isVisible = this is DataState.Success
        }
    }
}

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}

fun <T> DataState<T>.onLoading(
    execute: () -> Unit
): DataState<T> = apply {
    if (this is DataState.Loading) {
        execute()
    }
}

fun <T> DataState<T>.onError(
    execute: (error: Throwable?) -> Unit
): DataState<T> = apply {
    if (this is DataState.Error) {
        execute(exception)
    }
}

fun <T> DataState<T>.onEmpty(
    execute: () -> Unit
): DataState<T> = apply {
    if (this is DataState.Empty) {
        execute()
    }
}

fun <T> DataState<T>.onErrorNetwork(
    execute: (error: IOException?) -> Unit
): DataState<T> = apply {
    if (this is DataState.ErrorNetwork) {
        execute(errorConnection)
    }
}

fun <T> DataState<T>.onErrorHttpException(
    execute: (error: HttpException?) -> Unit
): DataState<T> = apply {
    if (this is DataState.ErrorHttpException) {
        execute(errorCode)
    }
}

fun <T> DataState<T>.onSuccess(
    execute: (data: T) -> Unit
): DataState<T> = apply {
    if (this is DataState.Success) {
        execute(data)
    }
}
