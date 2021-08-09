package com.sofit

import android.app.Application
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelperlib.BuildConfig
import com.sofit.adshelperlib.R

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AdsHelper.Builder(applicationContext)
            .with(applicationContext)
            .isVerified(true)
            .isDebugMode(BuildConfig.DEBUG)
            .adMobAppId(applicationContext.getString(R.string.AdMob_app_id))
            .adMobBannerId(applicationContext.getString(R.string.AdMob_test_id_banner))
            .adMobInterstitialId(applicationContext.getString(R.string.AdMob_test_id_interstitial))
            .adMobNativeId(applicationContext.getString(R.string.AdMob_test_id_native))
            .build()
    }
}