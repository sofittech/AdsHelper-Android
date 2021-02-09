package com.sofit.adshelper.mainclass

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.InterstitialAd
import com.sofit.adshelper.allAds.AdMobBanner
import com.sofit.adshelper.allAds.FacebookBanner
import com.sofit.adshelper.allAds.LoadAdMobIntAd
import com.sofit.adshelper.allAds.LoadFacebookIntAd

object AdsHelper {
    lateinit var mInterstitialAd: InterstitialAd
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var fb_banner_id: String
    lateinit var AdMob_banner_id: String
    lateinit var appContext: Context
    var isUserVerified: Boolean = false

    data class Builder(
        var context: Context,
        var AdMob_native_id: String? = null,
        var fb_native_id: String? = null,
        var AdMob_app_id: String? = null
    ) {
        fun with(context: Context) = apply {
            this.context = context
            appContext = context
            AudienceNetworkAds.initialize(context)
        }

        fun isVerified(isVerified: Boolean) = apply {
            isUserVerified = isVerified
        }

        fun adMobAppId(AdMobApp: String) = apply { this.AdMob_app_id = AdMobApp }
        fun adMobInterstitialId(AdMobInterstitial: String) = apply {
            AudienceNetworkAds.initialize(context);
            mInterstitialAd = InterstitialAd(context)
            mInterstitialAd.adUnitId = AdMobInterstitial
        }

        fun adMobBannerId(AdMobBanner: String) = apply { AdMob_banner_id = AdMobBanner }
        fun adMobNativeId(AdMobNative: String) = apply { this.AdMob_native_id = AdMobNative }
        fun fbInterstitialID(fbInterstitial: String) = apply {
            facebookInterstitialAd = com.facebook.ads.InterstitialAd(context, fbInterstitial)
        }

        fun fbBannerId(fbBanner: String) = apply { fb_banner_id = fbBanner }
        fun fbNativeId(fbNative: String) = apply { this.fb_native_id = fbNative }
        fun build() = AdsHelper

    }

    @JvmStatic
    fun loadFacebookInterstitial(autoLoadNextTime: Boolean) {
        if (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated)
            if (isUserVerified) {
                LoadFacebookIntAd.loadFbAd(autoLoadNextTime)
            } else {
                Log.e("facebookInterstitial", "Ad already loaded")
            }
    }

    @JvmStatic
    fun showFacebookInterstitial(context: Context) {
        if (facebookInterstitialAd.isAdLoaded && isUserVerified) {
            facebookInterstitialAd.show()
            Log.e("facebookInter", "showingFacebookInterstitialAd")
        }
    }

    @JvmStatic
    fun loadAdMobInterstitial(autoLoadNextTime: Boolean) {
        if (!mInterstitialAd.isLoaded && isUserVerified) {
            LoadAdMobIntAd.loadAdMobAd(autoLoadNextTime)
        } else {
            Log.e("AdMobInterstitial", "AdMob Already loaded")
        }
    }

    @JvmStatic
    fun showAdMobInterstitial(context: Context) {
        Log.e("showInterAd", "running")
        if (mInterstitialAd.isLoaded && isUserVerified) {
            Log.e("showing", "ad")
            mInterstitialAd.show()
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

}

