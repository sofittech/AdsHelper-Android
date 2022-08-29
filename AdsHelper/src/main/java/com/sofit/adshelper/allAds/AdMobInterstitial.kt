package com.sofit.adshelper.allAds

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sofit.adshelper.core.AdsHelper
import timber.log.Timber

object AdMobInterstitial {

    fun loadAdMobAd(activity: Activity, ids: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity,
            ids,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    AdsHelper.adMobInterstitialAd = interstitialAd
                    Timber.e("AdMob Interstitial Loaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    AdsHelper.adMobInterstitialAd = null
                    Timber.e("AdMob Interstitial ${loadAdError.message}")
                }
            })
    }
}