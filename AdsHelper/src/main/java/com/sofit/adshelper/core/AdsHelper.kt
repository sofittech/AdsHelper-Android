package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.allAds.*
import com.sofit.adshelper.enums.AdNetwork
import com.sofit.adshelper.helper.MyMoPub

object AdsHelper {
    var adMobInterstitialAd: InterstitialAd? = null
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var facebookBannerId: String
    lateinit var adMobBannerId: String
    lateinit var moPubBannerId: String
    lateinit var moPubInterstitialID: String
    lateinit var adMobNativeId: String
    lateinit var adMobInterstitialId: String
    lateinit var appContext: Context
    var isUserVerified: Boolean = false

    data class Builder(
        var context: Context,
        var adMobNativeId: String? = null,
        var fb_native_id: String? = null,
        var AdMob_app_id: String? = null
    ) {
        fun with(context: Context) = apply {
            this.context = context
            appContext = context
            AudienceNetworkAds.initialize(context)
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
        fun moPubBannerId(MoPubBanner: String) = apply {
            moPubBannerId = MoPubBanner
            MyMoPub().init(context, moPubBannerId)
        }

        fun moPubInterstitialId(MoPubInterstitial: String) = apply {
            moPubInterstitialID = MoPubInterstitial
            MyMoPub().init(context, moPubInterstitialID)
        }

        fun adMobNativeId(AdMobNative: String) = apply {
            this.adMobNativeId = AdMobNative
            adMobNativeId = AdMobNative
        }

        fun fbInterstitialID(fbInterstitial: String) = apply {
            facebookInterstitialAd = com.facebook.ads.InterstitialAd(context, fbInterstitial)
        }

        fun fbBannerId(fbBanner: String) = apply { facebookBannerId = fbBanner }

        fun fbNativeId(fbNative: String) = apply { this.fb_native_id = fbNative }

        fun build() = AdsHelper
    }

    @JvmStatic
    fun loadFacebookInterstitial() {
        if (this::facebookInterstitialAd.isInitialized &&
            (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated)
        ) {
            if (isUserVerified) {
                FacebookInterstitial.loadFbAd()
            } else {
                Log.e("facebookInterstitial", "Ad already loaded")
            }
        } else {
            Log.e("facebookInterstitial", "Ad already loaded")
        }
    }

    @JvmStatic
    private fun showFacebookInterstitial(context: Context) {
        if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded && isUserVerified) {
            facebookInterstitialAd.show()
            Log.e("facebookInter", "showingFacebookInterstitialAd")
        }
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
    fun loadMoPubInterstitial(activity: Activity) {
       MoPubInterstitial.loadAd(activity)
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
    fun showInterstitialAd(context: Activity, preferredNetwork: AdNetwork) {
        when (preferredNetwork) {
            AdNetwork.AdMob -> {
                if (adMobInterstitialAd != null) {
                    showAdMobInterstitial(context)
                } else if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded){
                    showFacebookInterstitial(context)

                } else if (MoPubInterstitial.moPubInterstitial.isReady){
                    MoPubInterstitial.showMoPubInterstitial()
                    }
            }
            AdNetwork.Facebook -> {
                if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded)
                    showFacebookInterstitial(context)
                else
                    showAdMobInterstitial(context)
            }
            AdNetwork.MoPub -> {
                MoPubInterstitial.showMoPubInterstitial()
            }
        }
    }

    @JvmStatic
    fun showAdMobBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            AdMobBanner.showAdMobBanner(activity, rLayout)
        }
    }

    @JvmStatic
    fun showMoPubBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            MoPubBanner.showMoPubBanner(activity, rLayout)
        }
    }

    @JvmStatic
    fun showFacebookBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            FacebookBanner.showFacebookBanner(activity, rLayout)
        }
    }

    @JvmStatic
    fun showAdMobNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        AdMobNativeView.showNativeAd(context, frameLayout)
    }

}

