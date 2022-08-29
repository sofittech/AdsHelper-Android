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

object AdsHelper {
    var adMobInterstitialAd: InterstitialAd? = null
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var facebookBannerId: String
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

        fun adMobNativeId(AdMobNative: String) = apply {
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
    private fun showFacebookInterstitial() {
        if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded && isUserVerified) {
            facebookInterstitialAd.show()
            Log.e("facebookInter", "showingFacebookInterstitialAd")
        }
    }

    @JvmStatic
    fun loadNativeInterstitialAds(activity: Activity, adNetwork: AdNetwork) {
        when (adNetwork) {
            AdNetwork.AdMob -> {
                if (adMobInterstitialAd == null && isUserVerified) {
                    AdMobInterstitial.loadAdMobAd(activity)
                } else {
                    Log.e("admob", "AdMob Already loaded")
                }
            }
            AdNetwork.Facebook -> {
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
    fun showInterstitialAd(context: Activity, preferredNetwork: AdNetwork) {
        when (preferredNetwork) {
            AdNetwork.AdMob -> {
                if (adMobInterstitialAd != null) {
                    showAdMobInterstitial(context)
                } else {
                    showFacebookInterstitial()

                }
            }
            AdNetwork.Facebook -> {
                if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded)
                    showFacebookInterstitial()

            }
        }
    }

    @JvmStatic
    fun showNativeBannerAds(activity: Activity, rLayout: RelativeLayout, adNetwork: AdNetwork) {
        when (adNetwork) {
            AdNetwork.AdMob -> {
                if (isUserVerified) {
                    AdMobBanner.showAdMobBanner(activity, rLayout)
                }
            }
            AdNetwork.Facebook -> {
                if (isUserVerified) {
                    FacebookBanner.showFacebookBanner(activity, rLayout)
                }
            }
        }

    }


    @JvmStatic
    fun showAdMobNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        AdMobNativeView.showNativeAd(context, frameLayout)
    }

}

