package com.alexqueudot.android.data.repository.items.error

import com.alexqueudot.android.data.result.DataError

/**
 * Created by alex on 2019-07-08.
 */

sealed class ItemsError : DataError

object NetworkUnavailable : ItemsError()
object Blacklisted : ItemsError()
object NotFound : ItemsError()

data class Unknown(val message: String?) : ItemsError()