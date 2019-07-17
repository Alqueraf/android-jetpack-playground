package com.alexqueudot.android.jetpackplayground

import android.app.Application
import com.alexqueudot.android.data.di.dataModule
import com.alexqueudot.android.jetpackplayground.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by alex on 2019-05-20.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // start Koin!
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            // Android context
            androidContext(this@App)
            // modules
            modules(listOf(appModule, dataModule))
        }
    }
}