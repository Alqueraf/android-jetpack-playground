package com.alexqueudot.android.data.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alex on 2019-07-05.
 */

fun createNetworkClient(baseUrl: String, debug: Boolean = false): Retrofit {
    return retrofitClient(
        baseUrl,
        httpClient(debug)
    )
}

private fun httpClient(debug: Boolean): OkHttpClient {
    // Logging interceptor
    val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        println(it)
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    // OkHttp Client
    val client = OkHttpClient.Builder()
    if (debug) {
        client.addInterceptor(logging)
    }
    // TODO: Add Stetho Interceptor
    return client.build()
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit {
    // Build Retrofit
    val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}

