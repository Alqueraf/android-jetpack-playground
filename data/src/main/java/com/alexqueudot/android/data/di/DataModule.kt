package com.alexqueudot.android.data.di

import com.alexqueudot.android.data.repository.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.datasource.remote.api.ApiDataSource
import com.alexqueudot.android.data.repository.datasource.remote.api.ApiEndpoints
import com.alexqueudot.android.data.repository.datasource.remote.sample.TestItemsDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alex on 2019-05-21.
 */

fun data(): DataComponent = DataModule

interface DataComponent {
    val apiDataSource: ApiDataSource
    val memoryDataSource: MemoryDataSource
}

object DataModule : DataComponent {

    // REMOTE SERVICE
    private val baseUrl = "https://api.stackexchange.com/2.2/"
    // Logging interceptor
    private val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        println(it)
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    // OkHttp Client
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    // Build Retrofit
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itemsApi by lazy { retrofit.create(ApiEndpoints::class.java) }
    override val apiDataSource = ApiDataSource(itemsApi)

    // MEMORY SERVICE
    override val memoryDataSource = MemoryDataSource()
}