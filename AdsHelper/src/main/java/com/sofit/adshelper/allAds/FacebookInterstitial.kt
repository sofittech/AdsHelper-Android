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
                // Interstitial ad displayed callback
                Log.e("facebook", "Banner: " + " Loaded")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                // Interstitial dismissed callback
                Log.e("facebook", "Interstitial: " + " Dismissed")
            }

            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Log.e("facebook", "Interstitial: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.e("facebook", "Interstitial: " + " Loaded")
                // Show the ad
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
            }
        }
        AdsHelper.facebookInterstitialAd.loadAd(
            AdsHelper.facebookInterstitialAd.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build()
        )
    }
}