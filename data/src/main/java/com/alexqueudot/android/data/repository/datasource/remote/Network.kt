package com.alexqueudot.android.data.repository.datasource.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alex on 2019-07-05.
 */

fun createNetworkClient(baseUrl: String, debug: Boolean = false): Retrofit {
    return retrofitClient(baseUrl, httpClient(debug))
}

private fun httpClient(debug: Boolean): OkHttpClient {
    // Logging interceptor
    val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        println(it)
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    // TODO: Stetho Interceptor
    // OkHttp Client
    val client = OkHttpClient.Builder()
    if (debug) {
        client.addInterceptor(logging)
    }
    return client.build()
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit {
    // Build Retrofit
    val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}

