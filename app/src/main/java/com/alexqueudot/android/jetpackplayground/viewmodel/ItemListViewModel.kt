package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.*
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ItemListViewModel(private val itemsRepository: ItemsRepository) : BaseViewModel() {

    val items: MutableLiveData<List<Item>> = MutableLiveData()

    fun refreshItems(refresh: Boolean = false) {
        val disposable = GetItemListUseCase(itemsRepository, refresh)
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
