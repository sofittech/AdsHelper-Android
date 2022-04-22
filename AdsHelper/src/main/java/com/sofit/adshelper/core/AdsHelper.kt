package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.allAds.AdMobBanner
import com.sofit.adshelper.allAds.AdMobInterstitial
import com.sofit.adshelper.allAds.AdMobNativeView
import com.sofit.adshelper.allAds.AppOpenAdManager

object AdsHelper {
    var adMobInterstitialAd: InterstitialAd? = null
    lateinit var adMobBannerId: String
    lateinit var adMobNativeId: String
    lateinit var adMobInterstitialId: String
    lateinit var adMobOpenAdId: String
    lateinit var appOpenAdManager: AppOpenAdManager
    var appOpenAd: AppOpenAd? = null
    var isLoadingAd = false
    var isShowingAd = false

    /** Keep track of the time an app open ad is loaded to ensure you don't show an expired ad. */
//    var loadTime: Long = 0

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
            appOpenAdManager = AppOpenAdManager()
        }

        fun isVerified(isVerified: Boolean) = apply {
            isUserVerified = isVerified
        }

        fun adMobAppId(AdMobApp: String) = apply { this.AdMob_app_id = AdMobApp }

        fun adMobInterstitialId(AdMobInterstitial: String) = apply {
            adMobInterstitialId = AdMobInterstitial
        }

        fun adMobOpenAdId(AdMobOpenAds: String) = apply {
            adMobOpenAdId = AdMobOpenAds
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
    fun loadAdMobOpenAd(activity: Activity) {
        if (isUserVerified && appOpenAd == null) {
            appOpenAdManager.loadOpenAd(activity)
        } else {
            Log.e("openAd", "Open ad Already loaded")
        }

    }
    @JvmStatic
    fun showAdMobOpenAd(
        activity: Activity
    ) {
        isShowingAd = true
        if (appOpenAd != null) {
            appOpenAd?.show(activity)
            appOpenAd = null
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

