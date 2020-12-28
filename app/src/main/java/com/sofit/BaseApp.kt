package com.sofit

import android.app.Application
import com.sofit.adshelper.AdsHelper
import com.sofit.adshelperlib.R

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AdsHelper.Builder(context = applicationContext)
            .with(applicationContext)
            .adMobAppId(applicationContext.getString(R.string.admob_app_id))
            .adMobBannerId(applicationContext.getString(R.string.admob_test_id_banner))
            .adMobInterstitialId(applicationContext.getString(R.string.admob_test_id_interstitial))
            .adMobNativeId(applicationContext.getString(R.string.admob_test_id_native))
            .fbBannerId(applicationContext.getString(R.string.fb_test_id_banner))
            .fbInterstitialID(applicationContext.getString(R.string.fb_test_id_interstitial))
            .build()
    }
}