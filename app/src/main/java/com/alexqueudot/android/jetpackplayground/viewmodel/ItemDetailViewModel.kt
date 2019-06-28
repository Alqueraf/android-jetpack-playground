package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemUseCase
import com.alexqueudot.android.jetpackplayground.di.app
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ItemDetailViewModel(private val itemsRepository: ItemsRepository, val itemId: Int):
    BaseViewModel() {

    val item = MutableLiveData<Item>()

    init {
        refreshData()
    }

    private fun refreshData() {
        val disposable = GetItemUseCase(itemsRepository, itemId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(item::postValue) { onError(it) }
        disposables.add(disposable)
    }

    private fun onError(error: Throwable) {
        // TODO: Update UI
        Timber.w(error, "Error getting ItemDetail")
    }
}


class ItemDetailViewModelFactory(private val itemId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemDetailViewModel(itemsRepository = app().itemsRepository, itemId = itemId) as T
    }

}