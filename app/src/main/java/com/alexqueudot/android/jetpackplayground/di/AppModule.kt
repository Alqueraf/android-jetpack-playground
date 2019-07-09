package com.alexqueudot.android.jetpackplayground.di

import com.alexqueudot.android.jetpackplayground.viewmodel.ItemDetailViewModel
import com.alexqueudot.android.jetpackplayground.viewmodel.ItemListViewModel
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
    viewModel { ItemDetailViewModel (itemsRepository = get()) }

    // endregion
}
