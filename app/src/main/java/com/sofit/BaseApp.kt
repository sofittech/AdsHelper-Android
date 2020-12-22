package com.sofit

import android.app.Application
import com.sofit.adshelper.AdsHelper

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AdsHelper.Builder(context = applicationContext)
            .with(applicationContext)
//            .admobAppId("id")
//            .admobBannerId("id")
//            .admobInterstitialId("id")
//            .admobNativeId("id")
//            .fbBannerId("id")
            .fbInterstitialID("IMG_16_9_APP_INSTALL#338675584002340_338677477335484")
//            .fbNativeId("id")
//            .initAdmob()
            .build()

    }
}