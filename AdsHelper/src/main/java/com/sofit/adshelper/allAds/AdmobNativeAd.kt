package com.sofit.adshelper.allAds

import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.adView.AdmobNativeAdTemplateStyle
import com.sofit.adshelper.core.AdsHelper


object AdMobNativeView {

    fun showNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        MobileAds.initialize(context)


        //Initializing the AdLoader   objects
        val adLoader = AdLoader.Builder(context, AdsHelper.adMobNativeId)
            .forNativeAd { ad: NativeAd ->
                val styles =
                    AdmobNativeAdTemplateStyle.Builder().build()
                frameLayout.setStyles(styles)
                 // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                frameLayout.setNativeAd(ad)
                frameLayout.visibility = View.VISIBLE

            }
            .withAdListener(object : AdListener() {

                override fun onAdLoaded() {
                    Log.e("admob", " Native ad loaded")
                }

                override fun onAdOpened() {
                    Log.e("admob", " Native ad opened")

                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e("admob", " Native ad Failed")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build()
            )
            .build()
        adLoader.loadAd(AdRequest.Builder().build())


//        val adLoader = AdLoader.Builder(context, AdsHelper.adMobNativeId)
//            .forUnifiedNativeAd { ad: NativeAd ->
//                Log.e("AdMob Native", "Ad is loaded, showing ad...")
//
//                frameLayout.visibility = View.VISIBLE
//                frameLayout.setNativeAd(ad)
//            }
//            .withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    // Handle the failure by logging, altering the UI, and so on.
//                    Log.e("checkAds", adError.message)
//
//                }
//            })
//            .withNativeAdOptions(
//                NativeAdOptions.Builder()
//                    // Methods in the NativeAdOptions.Builder class can be
//                    // used here to specify individual options settings.
//                    .build()
//            )
//            .build()
//
//        adLoader.loadAd(AdRequest.Builder().build())

    }
}
