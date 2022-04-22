package com.sofit.adshelper.allAds

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelper.core.AdsHelper.appOpenAd
import com.sofit.adshelper.core.AdsHelper.isLoadingAd
import com.sofit.adshelper.core.AdsHelper.isShowingAd

class AppOpenAdManager {
    private val TAG = "AppOpenAdManager"
    fun loadOpenAd(context: Context) {
        if (isLoadingAd || isAdAvailable()) {
            return
        }

        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            AdsHelper.adMobOpenAdId,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    Log.e(TAG, "onAdLoaded.")
                    appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null
                            isShowingAd = false
                            Log.e(TAG, "onAdDismissedFullScreenContent.")
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            appOpenAd = null
                            isShowingAd = false
                            Log.e(
                                TAG,
                                "onAdFailedToShowFullScreenContent: " + adError.message
                            )
                        }

                        override fun onAdShowedFullScreenContent() {
                            Log.e(TAG, "onAdShowedFullScreenContent.")
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.e(TAG, "onAdFailedToLoad: " + loadAdError.message)
                }
            })
    }

    /** Check if ad exists and can be shown. */
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }


}