package com.sofit.adshelper.mainclass

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.InterstitialAd
import com.sofit.adshelper.allAds.*

object AdsHelper {
    lateinit var mInterstitialAd: InterstitialAd
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var fb_banner_id: String
    lateinit var AdMob_banner_id: String

    data class Builder(
        var context: Context,
        var AdMob_native_id: String? = null,
        var fb_native_id: String? = null,
        var AdMob_app_id:String?=null
    ) {
        fun with(context: Context) = apply {
            this.context = context
            AudienceNetworkAds.initialize(context)
        }

        fun adMobAppId(AdMobApp: String) = apply {this.AdMob_app_id=AdMobApp}
        fun adMobInterstitialId(AdMobInterstitial: String) = apply {
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

    fun loadFacebookInterstitial(autoLoadNextTime: Boolean) {
        if (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated)
            LoadFacebookIntAd.loadFbAd(autoLoadNextTime)
        else {
            Log.e("facebookInterstitial", "Ad already loaded")
        }
    }

    fun showFacebookInterstitial(context: Context) {
        if (UtilClass.verifyInstallerId(context)) {
            if (facebookInterstitialAd.isAdLoaded) {
                facebookInterstitialAd.show()
                Log.e("facebookInter", "showingFacebookInterstitialAd")
            }
        }
    }

    fun loadAdMobInterstitial(autoLoadNextTime: Boolean) {
        if (!mInterstitialAd.isLoaded) {
            LoadAdMobIntAd.loadAdMobAd(autoLoadNextTime)
        } else {
            Log.e("AdMobInterstitial", "AdMob Already loaded")
        }
    }

    fun showAdMobInterstitial(context: Context) {
        Log.e("showInterAd", "running")
        if (UtilClass.verifyInstallerId(context)) {
            if (mInterstitialAd.isLoaded) {
                Log.e("showing", "ad")
                mInterstitialAd.show()
            }
        }
    }

    fun showAdMobBanner(activity: Activity, rLayout: RelativeLayout) {
        if (UtilClass.verifyInstallerId(activity)) {
            AdMobBanner.showAdMobBanner(activity, rLayout)
        }
    }

    fun showFacebookBanner(activity: Activity, rLayout: RelativeLayout) {
        if (UtilClass.verifyInstallerId(activity)) {
            FacebookBanner.showFacebookBanner(activity, rLayout)
        }
    }

}

