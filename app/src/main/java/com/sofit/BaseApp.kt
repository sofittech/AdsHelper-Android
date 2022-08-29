package com.sofit

import android.app.Application
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelperlib.BuildConfig
import com.sofit.adshelperlib.R
import timber.log.Timber

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AdsHelper.Builder(applicationContext)
            .with(applicationContext)
            .isVerified(true)
            .isDebugMode(BuildConfig.DEBUG)
            .adMobAppId(applicationContext.getString(R.string.AdMob_app_id))
            .adMobBannerId(applicationContext.getString(R.string.AdMob_test_id_banner))
            .adMobNativeId(applicationContext.getString(R.string.AdMob_test_id_native))
            .build()
    }
}