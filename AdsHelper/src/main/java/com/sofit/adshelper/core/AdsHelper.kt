package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.allAds.*
import java.util.*

object AdsHelper {
    var adMobInterstitialAd: InterstitialAd? = null
    lateinit var adMobBannerId: String
    lateinit var adMobNativeId: String
    lateinit var adMobInterstitialId: String
    lateinit var appContext: Context
    var isUserVerified: Boolean = false

    data class Builder(
        var context: Context,
        var fb_native_id: String? = null,
        var AdMob_app_id: String? = null
    ) {
        fun with(context: Context) = apply {
            this.context = context
            appContext = context
//            val testDeviceIds = listOf("C5A4692C6B784ADFB2134219B8908E27")
//            val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//            MobileAds.setRequestConfiguration(configuration)
            MobileAds.initialize(context)
        }

        fun isVerified(isVerified: Boolean) = apply {
            isUserVerified = isVerified
        }

        fun adMobAppId(AdMobApp: String) = apply { this.AdMob_app_id = AdMobApp }

        fun adMobInterstitialId(AdMobInterstitial: String) = apply {
            adMobInterstitialId = AdMobInterstitial
        }

        fun adMobBannerId(AdMobBanner: String) = apply { adMobBannerId = AdMobBanner }

        fun adMobNativeId(AdMobNative: String) = apply {
            adMobNativeId = AdMobNative
        }

        fun build() = AdsHelper
    }


    @JvmStatic
    fun loadAdMobInterstitial(activity: Activity) {
        if (adMobInterstitialAd == null && isUserVerified) {
            AdMobInterstitial.loadAdMobAd(activity)
        } else {
            Log.e("admob", "AdMob Already loaded")
        }
    }

    @JvmStatic
    private fun showAdMobInterstitial(context: Activity) {
        if (adMobInterstitialAd != null) {
            Log.e("admob", "running")
            adMobInterstitialAd?.show(context)
            adMobInterstitialAd = null

        }
    }

    @JvmStatic
    fun showInterstitialAd(context: Activity) {
        if (adMobInterstitialAd != null) {
            showAdMobInterstitial(context)
        }
    }

    @JvmStatic
    fun showAdMobBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            AdMobBanner.showAdMobBanner(activity, rLayout)
        }
    }


    @JvmStatic
    fun showAdMobNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        AdMobNativeView.showNativeAd(context, frameLayout)
    }

}

