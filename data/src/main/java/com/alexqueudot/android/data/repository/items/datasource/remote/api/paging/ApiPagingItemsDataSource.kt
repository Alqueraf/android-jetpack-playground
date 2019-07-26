package com.alexqueudot.android.data.repository.items.datasource.remote.api.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiEndpoints
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiItem
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiResponse
import com.alexqueudot.android.data.repository.items.error.Blacklisted
import com.alexqueudot.android.data.repository.items.error.NetworkUnavailable
import com.alexqueudot.android.data.repository.items.error.Unknown
import com.alexqueudot.android.data.result.DataError
import com.alexqueudot.android.data.result.Failure
import com.alexqueudot.android.data.result.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by alex on 2019-07-25.
 */

class ApiPagingItemsDataSource(private val apiEndpoints: ApiEndpoints) : PageKeyedDataSource<Int, Item>() {

    val errors = MutableLiveData<DataError>()
    val initialLoad = MutableLiveData<Boolean>()

    suspend fun getItems(page: Int): com.alexqueudot.android.data.result.Result<ApiResponse<ApiItem>> {
        return try {
            val data = apiEndpoints.getCharacters(page)
            Success(data = data)
        } catch (e: Throwable) {
            when (e) {
                is IOException -> Failure(error = NetworkUnavailable)
                is HttpException -> {
                    when (e.code()) {
                        403 -> Failure(Blacklisted)
                        else -> Failure(Unknown(e.message()))
                    }
                }
                else -> Failure(Unknown(e.message))
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        initialLoad.postValue(true)
        val request = GlobalScope.async(Dispatchers.IO) {
            return@async getItems(1)
        }
        runBlocking {
            when (val result = request.await()) {
                is Success -> callback.onResult(
                    result.data.results?.map { it.toItem() } ?: emptyList(),
                    result.data.info?.previousPage,
                    result.data.info?.nextPage)
                is Failure -> errors.postValue(result.error)
            }
            initialLoad.postValue(false)
        }
        println("++ loadInitial ENDED ++")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        println("++ loadAfter ++")
        val request = GlobalScope.async(Dispatchers.IO) {
            return@async getItems(params.key)
        }
        runBlocking {
            when (val result = request.await()) {
                is Success -> callback.onResult(result.data.results?.map { it.toItem() } ?: emptyList(),
                    result.data.info?.nextPage)
                is Failure -> errors.postValue(result.error)
            }
            initialLoad.postValue(false)
        }
        println("++ loadAfter ENDED ++")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        // Do nothing, won't start from the middle
    }
}