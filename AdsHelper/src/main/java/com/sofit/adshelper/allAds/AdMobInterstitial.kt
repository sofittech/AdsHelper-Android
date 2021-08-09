package com.sofit.adshelper.allAds

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sofit.adshelper.core.AdsHelper

object AdMobInterstitial {

    fun loadAdMobAd(activity: Activity) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity,
            AdsHelper.adMobInterstitialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    AdsHelper.adMobInterstitialAd = interstitialAd

                    Log.e("admob", "Interstitial: Loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    AdsHelper.adMobInterstitialAd = null
                    Log.e("admob", "Interstitial: " + loadAdError.message)
                }
            })
    }
//  we may use this code in future
//
// if (autoLoadNextTime) {
//        Handler(Looper.getMainLooper()).postDelayed(
//            { AdsHelper.adMobInterstitialAd.loadAd(AdRequest.Builder().build()) },
//            15000
//        )
//    }
}