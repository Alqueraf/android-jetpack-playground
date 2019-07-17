package com.alexqueudot.android.jetpackplayground.di

import androidx.navigation.NavController
import com.alexqueudot.android.jetpackplayground.items.detail.ItemDetailViewModel
import com.alexqueudot.android.jetpackplayground.items.ItemListViewModel
import com.alexqueudot.android.jetpackplayground.navigation.ItemsNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by alex on 2019-05-21.
 */

val appModule = module {
    // region: ViewModels

    // ListViewModel
    viewModel { (navigator: ItemsNavigator) -> ItemListViewModel(itemsRepository = get(), navigator = navigator) }
    // DetailViewModel
    viewModel { ItemDetailViewModel(itemsRepository = get()) }

    // endregion
}
