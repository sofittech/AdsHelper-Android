package com.sofit.adshelper.allAds

import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.sofit.adshelper.adView.AdmobNativeAdTemplateStyle
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.core.AdsHelper


object AdMobNativeView {

    fun showNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        MobileAds.initialize(context)
        val adLoader = AdLoader.Builder(context, AdsHelper.adMobNativeId)
            .forNativeAd { ad: NativeAd ->
                val styles =
                    AdmobNativeAdTemplateStyle.Builder().build()
                frameLayout.visibility = View.VISIBLE
                frameLayout.setStyles(styles)
                frameLayout.setNativeAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    Log.e("admob", " Native ad loaded")
                }

                override fun onAdOpened() {
                    Log.e("admob", " Native ad opened")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e("admob", " Native ad Failed $adError")
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
