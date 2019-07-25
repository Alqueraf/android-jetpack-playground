package com.alexqueudot.android.data.di

import com.alexqueudot.android.data.BuildConfig
import com.alexqueudot.android.data.repository.items.DataItemsRepository
import com.alexqueudot.android.data.repository.items.ItemsRepository
import com.alexqueudot.android.data.repository.items.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiItemsDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiEndpoints
import com.alexqueudot.android.data.utils.createNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by alex on 2019-05-21.
 */
private const val BASE_URL = "https://rickandmortyapi.com/api/"//"https://api.stackexchange.com/2.2/"

val dataModule = module {
    // ItemsRepository DataSources
    single { MemoryDataSource() }
    single {
        ApiItemsDataSource(
            apiEndpoints = get()
        )
    }
    single<ApiEndpoints> {
        createNetworkClient(BASE_URL, BuildConfig.DEBUG).create(ApiEndpoints::class.java)
    }

    // single instance of ItemsRepository
    single<ItemsRepository> {
        DataItemsRepository(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}
