package com.sofit

import android.app.Application
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelperlib.R

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AdsHelper.Builder(applicationContext)
            .with(applicationContext)
            .isVerified(true)
            .adMobAppId(applicationContext.getString(R.string.AdMob_app_id))
            .adMobBannerId(applicationContext.getString(R.string.AdMob_test_id_banner))
            .adMobInterstitialId(applicationContext.getString(R.string.AdMob_test_id_interstitial))
            .adMobNativeId(applicationContext.getString(R.string.AdMob_test_id_native))
            .fbBannerId(applicationContext.getString(R.string.fb_test_id_banner))
            .fbInterstitialID(applicationContext.getString(R.string.fb_test_id_interstitial))
            .moPubBannerId(applicationContext.getString(R.string.mopub_banner_test))
            .moPubInterstitialId(applicationContext.getString(R.string.mopub_interstitial_test))
            .build()
    }
}