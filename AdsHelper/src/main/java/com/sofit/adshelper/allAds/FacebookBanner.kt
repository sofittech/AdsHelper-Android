package com.sofit.adshelper.allAds

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.facebook.ads.*
import com.sofit.adshelper.core.AdsHelper

object FacebookBanner {
    fun showFacebookBanner(activity: Activity, bannerContainer: RelativeLayout) {
//        val facebookAdView = AdView(activity, AdsHelper.facebookBannerId, AdSize.BANNER_HEIGHT_50)
//        bannerContainer.addView(facebookAdView)
//        val adListener = object : AdListener {
//            override fun onError(ad: Ad, adError: AdError) {
//                Log.e(
//                    "facebookBannerAd",
//                    "onError-Banner failed: " + adError.errorMessage + " in " + activity.localClassName
//                )
//            }
//
//            override fun onAdLoaded(ad: Ad) {
//                bannerContainer.visibility=View.VISIBLE
//                Log.e("facebookBannerAd", "onAdLoaded" + " in " + activity.localClassName)
//            }
//
//            override fun onAdClicked(ad: Ad) {
//                Log.e("facebookBannerAd", "onAdClicked")
//            }
//
//            override fun onLoggingImpression(ad: Ad) {
//                Log.e("facebookBannerAd", "onLoggingImpression")
//            }
//        }
//        val loadAdConfig = facebookAdView.buildLoadAdConfig()
//            .withAdListener(adListener)
//            .build()
//        facebookAdView.loadAd(loadAdConfig)

    }
}