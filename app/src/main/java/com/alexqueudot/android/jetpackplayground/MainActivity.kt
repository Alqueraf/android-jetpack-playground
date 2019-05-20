package com.alexqueudot.android.jetpackplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexqueudot.android.core.usecase.GetItemsUseCase
import com.alexqueudot.android.data.repository.DataItemsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        refresh()
        refreshButton.setOnClickListener {
            refresh()
        }
    }

    fun refresh() {
        val observable = GetItemsUseCase(DataItemsRepository.instance)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items -> testText.text = items?.firstOrNull()?.title },
                { e -> Timber.e(e) }
            )
        disposables.add(observable)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
