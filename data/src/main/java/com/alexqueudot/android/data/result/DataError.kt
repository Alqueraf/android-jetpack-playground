package com.alexqueudot.android.data.result

/**
 * Created by alex on 2019-07-08.
 */

// Generic errors from the data layer
sealed class DataError: Throwable() {
    object NetworkUnavailable : DataError()
    object Unauthorized : DataError()
    data class Unknown(override val message: String?) : DataError()

    abstract class FeatureError : DataError()
}