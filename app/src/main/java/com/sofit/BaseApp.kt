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
            .moPubNativeAd(applicationContext.getString(R.string.mopub_native_test))
            .moPubBannerId(applicationContext.getString(R.string.mopub_banner_test))
            .moPubInterstitialId(applicationContext.getString(R.string.mopub_interstitial_test))
            .build()
    }
}