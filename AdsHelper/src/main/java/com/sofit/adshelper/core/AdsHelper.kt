package com.sofit.adshelper.core

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.sofit.adshelper.adView.AdmobNativeAdTemplateStyle
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.enums.AdNetwork
import com.sofit.adshelper.utils.AdsUtils.getAdSize

object AdsHelper {
    var onClickClose: (() -> Unit)? = null

    //facebook listener
    private var interstitialAdListener: InterstitialAdListener? = null

    //admob
    var adMobInterstitialAd: InterstitialAd? = null

    //applovin
    private var interstitialAd: MaxInterstitialAd? = null
    private val listener = object : MaxAdListener {
        override fun onAdLoaded(ad: MaxAd?) {
            Log.e("applovin", "onAdLoaded: loaded")

        }

        override fun onAdDisplayed(ad: MaxAd?) {
            Log.e("applovin", "onAdDisplayed: Displayed")
        }

        override fun onAdHidden(ad: MaxAd?) {
            Log.e("applovin", "onAdHidden: Hidden")
            onClickClose?.invoke()

        }

        override fun onAdClicked(ad: MaxAd?) {
            Log.e("applovin", "onAdClicked: Clicked")

        }

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
            Log.e("applovin", "onAdLoadFailed: LoadFailed")
            onClickClose?.invoke()

        }

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
            Log.e("applovin", "onAdDisplayFailed: DisplayFailed")
            onClickClose?.invoke()

        }

    }

    //facebook  id's
    var facebookInterstitialAd: com.facebook.ads.InterstitialAd? = null
    var facebookBannerId: String? = null

    //admob id's
    var adMobBannerId: String? = null
    var adMobNativeId: String? = null
    var adMobInterstitialId: String? = null

    //applovin id's
    var applovinInterstitialId: String? = null
    var appLovinBannerId: String? = null

    lateinit var appContext: Context
    var isUserVerified: Boolean = false

    class Builder(
        var context: Context,
        var fb_native_id: String? = null,
        var AdMob_app_id: String? = null
    ) {
        fun with(context: Context) = apply {
            this.context = context
            appContext = context
            AudienceNetworkAds.initialize(context)
            MobileAds.initialize(context)
            AppLovinSdk.getInstance(context).mediationProvider = "max"
            AppLovinSdk.initializeSdk(context) {
                // AppLovin SDK is initialized, start loading ads
            }
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

        fun appLovinInterstitialID(appLovinInterstitialID: String) = apply {
            applovinInterstitialId = appLovinInterstitialID
        }

        fun appLovinBannerID(appLovinBannerID: String) = apply {
            appLovinBannerId = appLovinBannerID
        }

        fun fbBannerId(fbBanner: String) = apply { facebookBannerId = fbBanner }

        fun fbNativeId(fbNative: String) = apply { this.fb_native_id = fbNative }

        fun build() = AdsHelper
    }


    @JvmStatic
    fun loadNativeInterstitialAds(activity: Activity, adNetwork: AdNetwork) {
        if (isUserVerified) {
            when (adNetwork) {
                AdNetwork.AdMob -> {
                    if (adMobInterstitialAd == null && isUserVerified) {
                        loadAdMobAd(activity)
                    } else {
                        Log.e("admob", "AdMob Already loaded")
                    }
                }
                AdNetwork.Facebook -> {
                    if (facebookInterstitialAd?.isAdLoaded == false || facebookInterstitialAd?.isAdInvalidated == true) {
                        if (isUserVerified) {
                            loadFbAd()
                        } else {
                            Log.e("facebookInterstitial", "Ad already loaded")
                        }
                    } else {
                        Log.e("facebookInterstitial", "Ad already loaded")
                    }
                }
                AdNetwork.AppLovin -> {
                    if (interstitialAd == null) {
                        applovinInterstitialId?.let {
                            interstitialAd = MaxInterstitialAd(it, activity)
                            interstitialAd?.let { inter ->
                                inter.setListener(listener)
                                inter.loadAd()
                            }
                        }

                    } else {
                        Log.e("applovin", "Applovin Already loaded")
                    }
                }
            }
        }


    }


    @JvmStatic
    fun showInterstitialAd(
        context: Activity,
        preferredNetwork: AdNetwork,
        goForward: (() -> Unit)
    ) {
        if (isUserVerified) {
            when (preferredNetwork) {
                AdNetwork.AdMob -> {
                    if (adMobInterstitialAd != null) {
                        adMobInterstitialAd?.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    goForward()
                                    Log.e("ads", "AdMob Interstitial was dismissed.")

                                }

                                override fun onAdFailedToShowFullScreenContent(p0: com.google.android.gms.ads.AdError) {
                                    goForward()
                                    Log.e("ads", "AdMob Interstitial was failed.")
                                    super.onAdFailedToShowFullScreenContent(p0)
                                }


                                override fun onAdShowedFullScreenContent() {
                                    Log.e("ads", "AdMob Interstitial was fullscreen content.")
                                    adMobInterstitialAd = null
                                }
                            }
                        adMobInterstitialAd?.show(context)
                        adMobInterstitialAd = null
                    } else {
                        goForward()
                        Log.e("else", "showAdMobInterstitial: call")
                    }
                }
                AdNetwork.Facebook -> {
                    if (facebookInterstitialAd?.isAdLoaded == true) {
                        facebookInterstitialAd?.show()
                        onClickClose = goForward
                    } else {
                        goForward.invoke()
                    }

                }
                AdNetwork.AppLovin -> {
                    if (interstitialAd != null) {
                        if (interstitialAd?.isReady == true) {
                            interstitialAd?.showAd()
                            onClickClose = goForward
                        } else {
                            goForward.invoke()
                        }
                    } else {
                        interstitialAd = null
                        goForward.invoke()
                    }
                }
            }
        }

    }

    @JvmStatic
    fun showNativeBannerAds(activity: Activity, rLayout: RelativeLayout, adNetwork: AdNetwork) {
        when (adNetwork) {
            AdNetwork.AdMob -> {
                if (isUserVerified) {
                    showAdMobBanner(activity, rLayout)
                }
            }
            AdNetwork.Facebook -> {
                if (isUserVerified) {
                    showFacebookBanner(activity, rLayout)
                }
            }
        }

    }

    @JvmStatic
    fun showAdMobNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        showNativeAd(context, frameLayout)
    }

    private fun loadAdMobAd(activity: Activity) {
        val adRequest = AdRequest.Builder().build()
        adMobInterstitialAd?.let {

        }
        adMobInterstitialId?.let {
            InterstitialAd.load(
                activity,
                it,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(p0: InterstitialAd) {
                        adMobInterstitialAd = p0
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        adMobInterstitialAd = null
                        Log.e("admob", "Interstitial: " + loadAdError.message)
                    }
                })
        }
    }

    private fun loadFbAd() {


        interstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                // Interstitial ad displayed callback
                Log.e("facebook", "interstitialAdListener: " + " Loaded")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                // Interstitial dismissed callback
                onClickClose?.invoke()
//                Log.e("facebook", "Interstitial: " + " Dismissed")
            }

            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Log.e("facebook", "Interstitial: error " + adError.errorMessage)
                onClickClose?.invoke()
            }

            override fun onAdLoaded(ad: Ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.e("facebook", "Interstitial: " + " Loaded")
                // Show the ad
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
            }
        }
        facebookInterstitialAd?.loadAd(
            facebookInterstitialAd?.buildLoadAdConfig()?.withAdListener(interstitialAdListener)
                ?.build()
        )
    }

    private fun showAdMobBanner(activity: Activity, adMobContainer: RelativeLayout) {
        val mAdView = AdView(activity)
        val adsSize = getAdSize(activity, mAdView)
        adsSize.let { mAdView.setAdSize(it) }
        adMobBannerId?.let {
            mAdView.adUnitId = it
        }
        adMobContainer.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adMobContainer.visibility = View.VISIBLE
                Log.e("admob", "Banner:  Loaded")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Log.e("admob", "Banner: " + loadAdError.message)
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
        mAdView.loadAd(adRequest)

    }

    private fun showFacebookBanner(activity: Activity, bannerContainer: RelativeLayout) {
        val facebookAdView = com.facebook.ads.AdView(
            activity,
            facebookBannerId,
            com.facebook.ads.AdSize.BANNER_HEIGHT_50
        )
        bannerContainer.addView(facebookAdView)
        val adListener = object : com.facebook.ads.AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                Log.e(
                    "facebookBannerAd",
                    "onError-Banner failed: " + adError.errorMessage + " in " + activity.localClassName
                )
            }

            override fun onAdLoaded(ad: Ad) {
                bannerContainer.visibility = View.VISIBLE
                Log.e("facebookBannerAd", "onAdLoaded" + " in " + activity.localClassName)
            }

            override fun onAdClicked(ad: Ad) {
                Log.e("facebookBannerAd", "onAdClicked")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.e("facebookBannerAd", "onLoggingImpression")
            }
        }
        val loadAdConfig = facebookAdView.buildLoadAdConfig()
            .withAdListener(adListener)
            .build()
        facebookAdView.loadAd(loadAdConfig)
    }

    private fun showNativeAd(context: Context, frameLayout: NativeAdCustomView) {
        MobileAds.initialize(context)
        val adLoader = adMobNativeId?.let {
            AdLoader.Builder(context, it)
                .forNativeAd { ad: NativeAd ->
                    val styles =
                        AdmobNativeAdTemplateStyle.Builder().build()
                    frameLayout.visibility = View.VISIBLE
                    frameLayout.setStyles(styles)
                    frameLayout.setNativeAd(ad)
                }
                .withAdListener(object : AdListener() {
                    override fun onAdLoaded() {
                        Log.e("admob", " Native ad loaded")
                    }

                    override fun onAdOpened() {
                        Log.e("admob", " Native ad opened")
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.e("admob", " Native ad Failed")
                    }
                })
                .withNativeAdOptions(
                    NativeAdOptions.Builder()
                        .build()
                )
                .build()
        }
        adLoader?.loadAd(AdRequest.Builder().build())
    }


}

