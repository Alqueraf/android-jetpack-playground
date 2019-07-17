package com.alexqueudot.android.data.repository.items.datasource.remote.api

import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.error.*
import com.alexqueudot.android.data.result.Result
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.data.result.DataError
import com.alexqueudot.android.data.result.Failure
import com.alexqueudot.android.data.result.Success
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by alex on 2019-05-20.
 */

class ApiItemsDataSource(private val apiEndpoints: ApiEndpoints) {

    suspend fun getItems(): Result<List<Item>> {
        return try {
            val data = apiEndpoints.getQuestions(site = "stackoverflow").items?.map { it.toItem() }.orEmpty()
            Success(data = data)
        } catch (e: Throwable) {
            when(e) {
                is IOException -> Failure(error = NetworkUnavailable)
                is HttpException -> {
                    when(e.code()) {
                        403 -> Failure(Blacklisted)
                        else -> Failure(Unknown(e.message()))
                    }
                }
                else -> Failure(Unknown(e.message))
            }
        }
    }

    suspend fun getItem(itemId: Int): Result<Item> {
        return when (val result = getItems()) {
            is Success -> {
                result.data.firstOrNull { it.id == itemId }?.let {
                    Success(data = it)
                } ?: Failure(error = NotFound)
            }
            is Failure -> result
        }
    }
}