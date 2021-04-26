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
            .fbBannerId(applicationContext.getString(R.string.fb_test_id_banner))
            .fbInterstitialID(applicationContext.getString(R.string.fb_test_id_interstitial))
            .build()
    }
}