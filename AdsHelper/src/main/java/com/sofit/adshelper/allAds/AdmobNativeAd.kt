package com.sofit.adshelper.allAds

import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.core.AdsHelper

object AdMobNativeView {

    fun showNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        MobileAds.initialize(context)
        //Initializing the AdLoader   objects
        val adLoader = AdLoader.Builder(context, AdsHelper.admobNativeId)
            .forUnifiedNativeAd { ad: UnifiedNativeAd ->
                Log.e("AdMob Native", "Ad is loaded, showing ad...")

                frameLayout.visibility = View.VISIBLE
                frameLayout.setNativeAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Log.e("checkAds", adError.message)

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

    }
}
