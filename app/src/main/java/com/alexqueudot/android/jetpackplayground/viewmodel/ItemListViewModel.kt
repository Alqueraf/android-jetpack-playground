package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.*
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemsUseCase
import com.alexqueudot.android.jetpackplayground.di.app
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ItemListViewModel(private val itemsRepository: ItemsRepository = app().itemsRepository) : BaseViewModel() {

    val items: MutableLiveData<List<Item>> = MutableLiveData()

    init {
        refreshItems()
    }

    fun refreshItems(refresh: Boolean = false) {
        val disposable = GetItemsUseCase(itemsRepository, refresh)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(items::postValue) { onError(it) }
        disposables.add(disposable)

    }
    private fun onError(error: Throwable) {
        // TODO: Update UI
        Timber.w(error, "Error getting Items")
    }

}
