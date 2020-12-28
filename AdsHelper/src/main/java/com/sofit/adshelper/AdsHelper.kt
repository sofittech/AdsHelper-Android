package com.sofit.adshelper

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.AdView
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.InterstitialAd
import com.sofit.adshelper.allAds.*
import javax.security.auth.login.LoginException

object AdsHelper {
    lateinit var mInterstitialAd: InterstitialAd
    lateinit var facebookInterstitialAd: com.facebook.ads.InterstitialAd
    lateinit var FACEBOOK_BANNER_ID: String
    lateinit var ADMOB_BANNER_ID: String


    data class Builder(
        var context: Context,
        var ADMOB_APP_ID: String? = null,
        var ADMOB_INTERSTITIAL_ID: String? = null,
        var ADMOB_NATIVE_ID: String? = null,
        var FACEBOOK_INTERSTITIAL_ID: String? = null,
        var FACEBOOK_NATIVE_ID: String? = null
    ) {
        fun with(context: Context) = apply { this.context = context
            AudienceNetworkAds.initialize(context)
         }
        fun admobAppId(adMobApp: String) = apply {}
        fun admobInterstitialId(admobInterstitial: String) = apply {
            mInterstitialAd = InterstitialAd(context)
            mInterstitialAd.adUnitId = admobInterstitial
            }

        fun admobBannerId(admobBanner: String) = apply {  ADMOB_BANNER_ID = admobBanner }
        fun admobNativeId(admobNative: String) = apply { this.ADMOB_NATIVE_ID = admobNative }
        fun fbInterstitialID(fbInterstitial: String) = apply {
            this.FACEBOOK_INTERSTITIAL_ID = fbInterstitial
            facebookInterstitialAd = com.facebook.ads.InterstitialAd(context, fbInterstitial)
        }

        fun fbBannerId(fbBanner: String) = apply { FACEBOOK_BANNER_ID = fbBanner }
        fun fbNativeId(fbNative: String) = apply { this.FACEBOOK_NATIVE_ID = fbNative }
        fun build() = AdsHelper

    }

    fun loadFacebookAd(autoLoadNextTime:Boolean) {
        if (!facebookInterstitialAd.isAdLoaded || facebookInterstitialAd.isAdInvalidated)
            LoadFacebookIntAd.loadFbAd(autoLoadNextTime)
        else {
            Log.e("facebookInterstitial", "Ad already loaded")
        }
    }

    fun showFacebookAd(context: Context) {
        if (UtilClass.verifyinstallerid(context)) {
            if (facebookInterstitialAd.isAdLoaded) {
                facebookInterstitialAd.show()
                Log.e("facebookInter", "showingFacebookInterstitialAd")
            }
        }
    }

    fun loadAdmobAd(autoLoadNextTime: Boolean) {
        if (!mInterstitialAd.isLoaded){
            LoadAdMobIntAd.loadAdmobAd(autoLoadNextTime)
        }else{
            Log.e("admobInterstitial","AdMob Already loaded")
        }
    }

    fun showAdmobAd(context: Context) {
        Log.e("showInterAd", "running")
        if (UtilClass.verifyinstallerid(context)){
            if (mInterstitialAd.isLoaded) {
                Log.e("showing", "ad")
                mInterstitialAd.show()
            }
        }
    }
    fun showAdmobBanner(activity:Activity,rLayout:RelativeLayout,context: Context){
        if (UtilClass.verifyinstallerid(context)){
            AdmobBanner.showAdmobBanner(activity,rLayout,context)
        }
    }
    fun showFacebookBanner(activity: Activity,rLayout: RelativeLayout,context: Context){
        if (UtilClass.verifyinstallerid(context)){
            FacebookBanner.showFacebookBanner(activity,rLayout,context)
        }
    }

}

