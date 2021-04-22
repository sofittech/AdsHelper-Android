package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.InterstitialAd
import com.sofit.adshelper.allAds.AdMobBanner
import com.sofit.adshelper.allAds.FacebookBanner
import com.sofit.adshelper.allAds.AdMobInterstitial
import com.sofit.adshelper.allAds.FacebookInterstitial
import com.google.android.gms.ads.MobileAds
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.allAds.*
import com.sofit.adshelper.enums.AdNetwork

object AdsHelper {
    lateinit var adMobInterstitialAd: InterstitialAd
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var facebookBannerId: String
    lateinit var adMobBannerId: String
    lateinit var adMobNativeId: String
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
            adMobInterstitialAd = InterstitialAd(context)
            adMobInterstitialAd.adUnitId = AdMobInterstitial
        }

        fun adMobBannerId(AdMobBanner: String) = apply { adMobBannerId = AdMobBanner }

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
    fun loadFacebookInterstitial(autoLoadNextTime: Boolean) {
        if (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated) {
            if (isUserVerified) {
                FacebookInterstitial.loadFbAd(autoLoadNextTime)
            } else {
                Log.e("facebookInterstitial", "Ad already loaded")
            }
        } else {
            Log.e("facebookInterstitial", "Ad already loaded")
        }
    }

    @JvmStatic
    private fun showFacebookInterstitial(context: Context) {
        if (facebookInterstitialAd.isAdLoaded && isUserVerified) {
            facebookInterstitialAd.show()
            Log.e("facebookInter", "showingFacebookInterstitialAd")
        }
    }

    @JvmStatic
    fun loadAdMobInterstitial(autoLoadNextTime: Boolean) {
        if (!adMobInterstitialAd.isLoaded && isUserVerified) {
            AdMobInterstitial.loadAdMobAd(autoLoadNextTime)
        } else {
            Log.e("AdMobInterstitial", "AdMob Already loaded")
        }
    }

    @JvmStatic
    private fun showAdMobInterstitial(context: Context) {
        Log.e("showInterAd", "running")
        if (adMobInterstitialAd.isLoaded && isUserVerified) {
            Log.e("showing", "ad")
            adMobInterstitialAd.show()
        }
    }

    @JvmStatic
    fun showInterstitialAd(context: Context, preferredNetwork: AdNetwork) {
        when (preferredNetwork) {
            AdNetwork.AdMob -> {
                if (adMobInterstitialAd.isLoaded) showAdMobInterstitial(context)
                else showFacebookInterstitial(context)
            }
            AdNetwork.Facebook -> {
                if (facebookInterstitialAd.isAdLoaded) showFacebookInterstitial(context)
                else showAdMobInterstitial(context)
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

