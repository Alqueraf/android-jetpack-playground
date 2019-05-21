package com.alexqueudot.android.jetpackplayground.di

import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.data.repository.DataItemsRepository

/**
 * Created by alex on 2019-05-21.
 */

fun app(): AppComponent = AppModule

interface AppComponent {
    val itemsRepository: ItemsRepository
}
object AppModule: AppComponent {
    override val itemsRepository = DataItemsRepository()
}