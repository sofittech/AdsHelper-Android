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
    fun loadNativeAd(context: Context, autoShow: Boolean, frameLayout: NativeAdCustomView) {
        MobileAds.initialize(context)
        val adLoader = AdLoader.Builder(context, AdsHelper.adMobNativeId)
            .forNativeAd { ad: NativeAd ->
                AdsHelper.adMobNativeAd = ad
                if (autoShow) {
                    val styles =
                        AdmobNativeAdTemplateStyle.Builder().build()
                    frameLayout.visibility = View.VISIBLE
                    frameLayout.setStyles(styles)
                    AdsHelper.adMobNativeAd?.let { frameLayout.setNativeAd(it) }
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    Timber.e("AdMob Native ad loaded")
                    if (autoShow) {
                        frameLayout.visibility = View.VISIBLE
                    }
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
