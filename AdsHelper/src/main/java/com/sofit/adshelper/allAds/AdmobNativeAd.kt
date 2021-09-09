package com.sofit.adshelper.allAds

import android.content.Context
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.sofit.adshelper.adView.AdmobNativeAdTemplateStyle
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.core.AdsHelper
import timber.log.Timber


object AdMobNativeView {
    fun loadNativeAd(context: Context) {
        MobileAds.initialize(context)
        val adLoader = AdLoader.Builder(context, AdsHelper.adMobNativeId)
            .forNativeAd { ad: NativeAd ->
                AdsHelper.adMobNativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    Timber.e("AdMob Native ad loaded")
                }

                override fun onAdOpened() {
                    Timber.e("AdMob Native ad opened")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.e("AdMob Native ad Failed")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }
}
