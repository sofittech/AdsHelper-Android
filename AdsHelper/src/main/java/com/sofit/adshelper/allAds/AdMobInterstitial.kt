package com.sofit.adshelper.allAds

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.sofit.adshelper.core.AdsHelper

object AdMobInterstitial {

    fun loadAdMobAd(autoLoadNextTime: Boolean) {
        AdsHelper.mInterstitialAd.loadAd(AdRequest.Builder().build())
        AdsHelper.mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.e(
                    "AdMobInterstitial",
                    "AdMob Interstitial ad is loaded and ready to be displayed!"
                )
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Log.e(
                    "AdMobInterstitial",
                    "onError-Banner failed, error code: $errorCode"
                )
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.e("AdMobInterstitial", "opened")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.e("AdMobInterstitial", "clicked")
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.e("AdMobInterstitial", "onAdLeftApplication")
            }

            override fun onAdClosed() {
                Log.e("AdMobInterstitial", "closed")

                if (autoLoadNextTime) {
                    Handler(Looper.getMainLooper()).postDelayed(
                        { AdsHelper.mInterstitialAd.loadAd(AdRequest.Builder().build()) },
                        15000
                    )
                }
            }
        }
    }

}