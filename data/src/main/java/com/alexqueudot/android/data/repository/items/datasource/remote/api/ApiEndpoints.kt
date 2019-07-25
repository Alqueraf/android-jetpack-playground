package com.alexqueudot.android.data.repository.items.datasource.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by alex on 2019-05-20.
 */

interface ApiEndpoints {
    @GET("character/")
    suspend fun getCharacters(@Query("page") page: Int): ApiResponse<ApiItem>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): ApiItem

}