package com.sofit.adshelper.allAds

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAdListener
import com.sofit.adshelper.core.AdsHelper

object FacebookInterstitial {
    fun loadFbAd(autoLoadNextTime: Boolean) {
        AdsHelper.facebookInterstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad dismissed.")

                if (autoLoadNextTime) {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            AdsHelper.facebookInterstitialAd.loadAd()                        },
                        15000
                    )
                }
            }

            override fun onError(ad: Ad, adError: AdError) {
                Log.e(
                    "facebookInterstitial",
                    "Interstitial ad failed to load: " + adError.errorMessage + " in "
                )
            }

            override fun onAdLoaded(ad: Ad) {
                Log.e(
                    "facebookInterstitial",
                    "Interstitial ad is loaded and ready to be displayed!"
                )
            }

            override fun onAdClicked(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad impression logged!")
            }
        })
        AdsHelper.facebookInterstitialAd.loadAd()
    }
}