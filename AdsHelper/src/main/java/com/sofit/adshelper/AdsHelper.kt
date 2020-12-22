package com.sofit.adshelper

import android.app.Activity
import android.content.Context
import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

object AdsHelper {
    lateinit var mInterstitialAd: InterstitialAd
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var context: Context
    lateinit var facebookAdView: AdView
    lateinit var activity: Activity

    fun initAdmob(context: Context) {
        MobileAds.initialize(context) {
        }
    }

    data class Builder(

        var context: Context,
        var ADMOB_APP_ID: String? = null,
        var ADMOB_INTERSTITIAL_ID: String? = null,
        var ADMOB_BANNER_ID: String? = null,
        var ADMOB_NATIVE_ID: String? = null,
        var FACEBOOK_INTERSTITIAL_ID: String? = null,
        var FACEBOOK_BANNER_ID: String? = null,
        var FACEBOOK_NATIVE_ID: String? = null
    ) {

        fun with(context: Context) = apply { this.context = context }
        fun admobAppId(adMobApp: String) = apply { this.ADMOB_APP_ID = adMobApp }
        fun admobInterstitialId(admobInterstitial: String) =
            apply { this.ADMOB_INTERSTITIAL_ID = admobInterstitial }

        fun admobBannerId(admobBanner: String) = apply { this.ADMOB_BANNER_ID = admobBanner }
        fun admobNativeId(admobNative: String) = apply { this.ADMOB_NATIVE_ID = admobNative }
        fun fbInterstitialID(fbInterstitial: String) = apply {
            this.FACEBOOK_INTERSTITIAL_ID = fbInterstitial
            facebookInterstitialAd =
                com.facebook.ads.InterstitialAd(context, fbInterstitial)
            loadFacebookInterstitial(context, false)
        }

        fun fbBannerId(fbBanner: String) = apply { this.FACEBOOK_BANNER_ID = fbBanner }
        fun fbNativeId(fbNative: String) = apply { this.FACEBOOK_NATIVE_ID = fbNative }
        fun initAdmob() = apply { initAdmob(context) }
        fun build() = AdsHelper

    }

    fun loadFacebookInterstitial(
        context: Context,
        showWhenLoaded: Boolean,
     ) {
        if (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated) loadFbAd(
            context,
            showWhenLoaded
        ) else {
            Log.e("facebookInterstitial", "Ad already loaded")
        }
    }

    private fun loadFbAd(context: Context, showWhenLoaded: Boolean) {
        facebookInterstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad, adError: AdError) {
                Log.e(
                    "facebookInterstitial",
                    "Interstitial ad failed to load: " + adError.errorMessage + " in " + activity.localClassName
                )
            }

            override fun onAdLoaded(ad: Ad) {
                Log.e(
                    "facebookInterstitial",
                    "Interstitial ad is loaded and ready to be displayed!"
                )
                if (showWhenLoaded) facebookInterstitialAd.show()
            }

            override fun onAdClicked(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.e("facebookInterstitial", "Interstitial ad impression logged!")
            }
        })
        facebookInterstitialAd.loadAd()
    }

    fun showFacebookInterstitialAd() {
        try {
            if (facebookInterstitialAd.isAdLoaded) {
                facebookInterstitialAd.show()
                Log.e("facebookInter", "showingFacebookInterstitialAd")
            }
        } catch (e: Exception) {
            Log.e("facebookInter", "Error")
            e.printStackTrace()
        }
    }

}

