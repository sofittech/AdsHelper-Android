package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.AudienceNetworkAds
import com.sofit.adshelper.allAds.FacebookBanner
import com.sofit.adshelper.allAds.FacebookInterstitial
import com.sofit.adshelper.enums.AdNetwork

object AdsHelper {
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
        }

        fun isVerified(isVerified: Boolean) = apply {
            isUserVerified = isVerified
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
        if (this::facebookInterstitialAd.isInitialized &&
            (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated)
        ) {
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
        if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded && isUserVerified) {
            facebookInterstitialAd.show()
            Log.e("facebookInter", "showingFacebookInterstitialAd")
        }
    }

    @JvmStatic
    fun showInterstitialAd(context: Context, preferredNetwork: AdNetwork) {
        if (preferredNetwork == AdNetwork.Facebook) {
            if (this::facebookInterstitialAd.isInitialized && facebookInterstitialAd.isAdLoaded)
                showFacebookInterstitial(context)
        }
    }

    @JvmStatic
    fun showFacebookBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            FacebookBanner.showFacebookBanner(activity, rLayout)
        }
    }
}

