package com.alexqueudot.android.data.unit.di

import com.alexqueudot.android.data.BuildConfig
import com.alexqueudot.android.data.repository.items.DataItemsRepository
import com.alexqueudot.android.data.repository.items.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiEndpoints
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiItemsDataSource
import com.alexqueudot.android.data.unit.mock.ApiEndpointsMock
import com.alexqueudot.android.data.utils.createNetworkClient
import org.koin.dsl.module

/**
 * Created by alex on 2019-07-17.
 */
private const val BASE_URL = "https://api.stackexchange.com/2.2/"

val testModule = module {
    // Memory DataSource
    single { MemoryDataSource() }

    // API DataSource
    single {
        ApiItemsDataSource(
            apiEndpoints = get()
        )
    }
    // Mock API Endpoints
    single<ApiEndpoints> {
        ApiEndpointsMock()
    }

    // Repository
    single{
        DataItemsRepository(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}