package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by alex on 2019-05-21.
 */

open class BaseViewModel: ViewModel() {
    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}