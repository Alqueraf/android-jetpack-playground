package com.alexqueudot.android.data.repository.items.datasource.remote.api

import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.result.Result
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.data.result.DataError
import com.alexqueudot.android.data.result.Failure
import com.alexqueudot.android.data.result.Success
import com.alexqueudot.android.data.utils.toDataError
import retrofit2.HttpException


/**
 * Created by alex on 2019-05-20.
 */

class ApiItemsDataSource(private val apiEndpoints: ApiEndpoints) {

    suspend fun getItems(): Result<List<Item>> {
        return try {
            val data = apiEndpoints.getQuestions(site = "stackoverflow").items?.map { it.toItem() }.orEmpty()
            Success(data = data)
        } catch (e: Throwable) {
            // Handle specific cases
            if (e is HttpException && e.code() == 403) {
                Failure(error = ItemsError.Blacklisted)
            } else {
                // Handle generic case
                Failure(error = e.toDataError())
            }
        }
    }

    suspend fun getItem(itemId: Int): Result<Item> {
        return when (val result = getItems()) {
            is Success -> {
                result.data.firstOrNull { it.id == itemId }?.let {
                    Success(data = it)
                } ?: Failure(error = ItemsError.NotFound)
            }
            is Failure -> result
        }
    }
}