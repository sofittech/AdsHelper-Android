package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.sofit.adshelper.allAds.MoPubBanner
import com.sofit.adshelper.allAds.MoPubInterstitial
import com.sofit.adshelper.allAds.MoPubNativeAd
import com.sofit.adshelper.helper.MyMoPub

object AdsHelper {
    lateinit var moPubBannerId: String
    lateinit var moPubInterstitialID: String
    lateinit var moPubNativeID: String
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
        }

        fun isVerified(isVerified: Boolean) = apply {
            isUserVerified = isVerified
        }

        fun moPubBannerId(MoPubBanner: String) = apply {
            moPubBannerId = MoPubBanner
            MyMoPub().init(context, moPubBannerId)
        }

        fun moPubInterstitialId(MoPubInterstitial: String) = apply {
            moPubInterstitialID = MoPubInterstitial
            MyMoPub().init(context, moPubInterstitialID)
        }

        fun moPubNativeAd(MoPubNativeAd: String) = apply {
            moPubNativeID = MoPubNativeAd
            MyMoPub().init(context, moPubNativeID)

        }

        fun build() = AdsHelper
    }

    @JvmStatic
    fun showMoPubNativeAd(activity: Activity, frameLayout: FrameLayout) {
        MoPubNativeAd.loadAd(activity, frameLayout)
    }

    @JvmStatic
    fun loadMoPubInterstitial(activity: Activity) {
        MoPubInterstitial.loadAd(activity)
    }

    @JvmStatic
    fun showMoPubBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            MoPubBanner.showMoPubBanner(activity, rLayout)
        }
    }

    @JvmStatic
    fun showInterstitialAd(context: Activity) {
        if (MoPubInterstitial.moPubInterstitial.isReady) {
            MoPubInterstitial.showMoPubInterstitial()
        }
    }
}

