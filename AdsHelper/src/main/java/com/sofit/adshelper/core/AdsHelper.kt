package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.google.android.ads.mediationtestsuite.MediationTestSuite
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.sofit.adshelper.adView.AdmobNativeAdTemplateStyle
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.allAds.AdMobBanner
import com.sofit.adshelper.allAds.AdMobInterstitial
import com.sofit.adshelper.allAds.AdMobNativeView
import timber.log.Timber

object AdsHelper {
    var adMobInterstitialAd: InterstitialAd? = null
    var adMobNativeAd: NativeAd? = null
    lateinit var adMobBannerId: String
    lateinit var adMobNativeId: String
    lateinit var adMobInterstitialId: String
    lateinit var appContext: Context
    var isUserVerified: Boolean = false
    var isDebugging: Boolean = false

    data class Builder(
        var context: Context,
        var AdMob_app_id: String? = null
    ) {
        fun with(context: Context) = apply {
            this.context = context
            appContext = context
            MobileAds.initialize(context)
        }

        fun isVerified(isVerified: Boolean) = apply {
            isUserVerified = isVerified
        }

        fun isDebugMode(isDebug: Boolean) = apply {
            isDebugging = isDebug
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
    fun showMediationTestSuite(context: Activity) {
        if (AdsHelper.isDebugging) {
            MediationTestSuite.launch(context)
        }
    }

    @JvmStatic
    fun loadInterstitial(activity: Activity) {
        if (adMobInterstitialAd == null && isUserVerified) {
            AdMobInterstitial.loadAdMobAd(activity)
        } else {
            Timber.e("AdMob Interstitial Already loaded")
        }
    }

    @JvmStatic
    private fun showAdMobInterstitial(context: Activity, goForward: () -> Unit) {
        if (adMobInterstitialAd != null) {
            adMobInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    goForward()
                    Timber.tag("ads").e("AdMob Interstitial was dismissed.")
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    goForward()
                    Timber.tag("ads").e("AdMob Interstitial failed to show.")
                    super.onAdFailedToShowFullScreenContent(p0)
                }


                override fun onAdShowedFullScreenContent() {
                    Timber.tag("ads").e("AdMob Interstitial showed fullscreen content.")
                    adMobInterstitialAd = null
                }
            }
            adMobInterstitialAd?.show(context)
            adMobInterstitialAd = null
        }
    }

    @JvmStatic
    fun showInterstitialAd(context: Activity, goForward: () -> Unit) {
        if (adMobInterstitialAd != null) {
            showAdMobInterstitial(context) {
                goForward()
                Timber.tag("ads").e("Done.")

            }
        }
    }

    @JvmStatic
    fun showBanner(activity: Activity, rLayout: RelativeLayout) {
        if (isUserVerified) {
            AdMobBanner.showAdMobBanner(activity, rLayout)
        }
    }

    @JvmStatic
    fun loadNativeAd(context: Context, autoShow: Boolean, frameLayout: NativeAdCustomView) {
        AdMobNativeView.loadNativeAd(context, autoShow, frameLayout)
    }

    @JvmStatic
    fun showNativeAd(frameLayout: NativeAdCustomView) {
        adMobNativeAd?.let {
            val styles =
                AdmobNativeAdTemplateStyle.Builder().build()
            frameLayout.visibility = View.VISIBLE
            frameLayout.setStyles(styles)
            frameLayout.setNativeAd(it)
        }
    }
}

