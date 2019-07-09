package com.alexqueudot.android.data.utils

import com.alexqueudot.android.data.result.DataError
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by alex on 2019-07-09.
 */

fun Throwable.toDataError(): DataError {

    when(this) {
        is IOException -> {
            return DataError.NetworkUnavailable
        }
        is HttpException -> {
            return when (this.code()) {
                401 -> {
                    DataError.Unauthorized
                }
                else -> {
                    DataError.Unknown(this.message)
                }
            }
        }
        else -> return DataError.Unknown(this.message)
    }
}