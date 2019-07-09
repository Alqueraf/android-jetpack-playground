package com.alexqueudot.android.data.result

/**
 * Created by alex on 2019-07-08.
 */

sealed class Result<out T : Any>

data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val error: Throwable?) : Result<Nothing>()



// Helper Inline functions
inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success) action(this.data)
    return this
}
inline fun <T : Any> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    if (this is Failure && error != null) action(this.error)
    return this
}