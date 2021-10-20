package com.sofit.adshelper.allAds

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelper.helper.MyMoPub

object MoPubInterstitial {
    @SuppressLint("StaticFieldLeak")
    lateinit var moPubInterstitial: MoPubInterstitial
    private lateinit var moPubInterstitialInterstitialAdListener: MoPubInterstitial.InterstitialAdListener
    fun loadAd(activity: Activity) {
        MyMoPub().init(activity, AdsHelper.moPubInterstitialID)
        Handler(Looper.getMainLooper()).postDelayed({
            loadMoPubInterstitial(activity)
        }, 100)
    }

    private fun loadMoPubInterstitial(activity: Activity) {
        moPubInterstitial = MoPubInterstitial(activity, AdsHelper.moPubInterstitialID)
        Log.e("mopubAd", "Interstitial ad is loading...")
        moPubInterstitial.load()

        moPubInterstitialInterstitialAdListener =
            object : MoPubInterstitial.InterstitialAdListener {
                override fun onInterstitialLoaded(interstitial: MoPubInterstitial?) {
                    Log.e("mopubAd", "Interstitial ad is loaded")
                }

                override fun onInterstitialShown(interstitial: MoPubInterstitial?) {
                    Log.e("mopubAd", "Interstitial ad has show")
                }

                override fun onInterstitialFailed(
                    interstitial: MoPubInterstitial?,
                    errorCode: MoPubErrorCode?
                ) {
                    Log.e("mopubAd", "Interstitial ad failed to load with error: $errorCode")
                }

                override fun onInterstitialDismissed(interstitial: MoPubInterstitial?) {
                    Log.e("mopubAd", "Interstitial ad has dismissed.")
                }

                override fun onInterstitialClicked(interstitial: MoPubInterstitial?) {
                    Log.e("mopubAd", "Interstitial ad clicked")
                }
            }
        moPubInterstitial.interstitialAdListener = moPubInterstitialInterstitialAdListener
    }

    fun showMoPubInterstitial() {
        if (this::moPubInterstitial.isInitialized) {
            if (moPubInterstitial.isReady) {
                moPubInterstitial.show()
            }
        }
    }
}

