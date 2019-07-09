package com.alexqueudot.android.jetpackplayground.di

import com.alexqueudot.android.jetpackplayground.items.detail.ItemDetailViewModel
import com.alexqueudot.android.jetpackplayground.items.ItemListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by alex on 2019-05-21.
 */

val appModule = module {
    // region: ViewModels

    // ListViewModel
    viewModel { ItemListViewModel(itemsRepository = get()) }
    // DetailViewModel
    viewModel { ItemDetailViewModel(itemsRepository = get()) }

    // endregion
}
