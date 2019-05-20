package com.alexqueudot.android.data.repository.remote

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by alex on 2019-05-20.
 */

class ApiService: ItemsRepository {

    private val itemsApi by lazy {
        // Logging interceptor
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            println(it)
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        // OkHttp Client
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        // Build Retrofit
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.stackexchange.com/2.2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(ItemsApi::class.java)
    }

    override fun getItems(): Single<List<Item>> {
        return itemsApi.getQuestions(site = "stackoverflow").map { it.items }.map { it.map { apiItem -> apiItem.toItem() } }
    }
}