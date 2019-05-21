package com.alexqueudot.android.data.repository.source.remote.api

import com.alexqueudot.android.data.repository.source.remote.ApiItemsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alex on 2019-05-20.
 */

interface ItemsApi {
    @GET("questions/")
    fun getQuestions(@Query("site") site: String): Single<ApiItemsResponse>
}