package com.alexqueudot.android.data.model

/**
 * Created by alex on 2019-05-20.
 */

data class Item (
    val id: Int,
    val title: String?,
    val url: String?,
    val score: Int?,
    val isAnswered: Boolean?
)
