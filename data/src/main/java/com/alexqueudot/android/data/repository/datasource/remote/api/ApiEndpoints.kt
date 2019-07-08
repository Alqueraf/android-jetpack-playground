package com.alexqueudot.android.data.repository.datasource.remote.api

import com.alexqueudot.android.data.repository.datasource.remote.ApiItemsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alex on 2019-05-20.
 */

interface ApiEndpoints {
    @GET("questions/")
    suspend fun getQuestions(@Query("site") site: String): ApiItemsResponse
}