package com.alexqueudot.android.jetpackplayground

import android.app.Application
import timber.log.Timber

/**
 * Created by alex on 2019-05-20.
 */

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}