package com.sofit.adshelper

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class AdsCoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}