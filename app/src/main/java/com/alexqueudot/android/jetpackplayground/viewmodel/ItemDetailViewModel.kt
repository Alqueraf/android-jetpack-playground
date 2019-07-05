package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.MutableLiveData
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ItemDetailViewModel(private val itemsRepository: ItemsRepository): BaseViewModel() {

    val item = MutableLiveData<Item>()

    fun refreshData(itemId: Int) {
        val disposable = GetItemDetailsUseCase(itemsRepository, itemId)
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

//
//class ItemDetailViewModelFactory(private val itemId: Int) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return ItemDetailViewModel(itemsRepository = app().itemsRepository, itemId = itemId) as T
//    }
//
//}