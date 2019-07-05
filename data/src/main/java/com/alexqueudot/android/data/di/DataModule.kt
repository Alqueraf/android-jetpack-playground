package com.alexqueudot.android.data.di

import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.data.BuildConfig
import com.alexqueudot.android.data.repository.DataItemsRepository
import com.alexqueudot.android.data.repository.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.datasource.remote.api.ApiDataSource
import com.alexqueudot.android.data.repository.datasource.remote.api.ApiEndpoints
import com.alexqueudot.android.data.repository.datasource.remote.createNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by alex on 2019-05-21.
 */
private const val BASE_URL = "https://api.stackexchange.com/2.2/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)
private val itemsApiEndpoints = retrofit.create(ApiEndpoints::class.java)

val dataSourceModule = module {
    single<MemoryDataSource> { MemoryDataSource() }
    single<ApiDataSource> { ApiDataSource(apiEndpoints = itemsApiEndpoints) }
}

val repositoryModule = module {

    // single instance of ItemsRepository
    single<ItemsRepository> { DataItemsRepository(localDataSource = get(), remoteDataSource = get()) }

}