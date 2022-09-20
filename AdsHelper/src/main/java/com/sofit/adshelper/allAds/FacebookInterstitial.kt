package com.sofit.adshelper.allAds

import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAdListener
import com.sofit.adshelper.core.AdsHelper

object FacebookInterstitial {
    fun loadFbAd() {
        val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                Log.e("facebook", "Banner: " + "loaded")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                Log.e("facebook", "Interstitial: " + " dismissed")
            }

            override fun onError(ad: Ad, adError: AdError) {
                Log.e("facebook", "interstitial: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                Log.e("facebook", "interstitial: " + " loaded")
            }

            override fun onAdClicked(ad: Ad) {
            }

            override fun onLoggingImpression(ad: Ad) {
            }
        }
        AdsHelper.facebookInterstitialAd?.let { int ->
            int.loadAd(int.buildLoadAdConfig().withAdListener(interstitialAdListener).build())
        }

    }
}