package com.sofit.adshelper.allAds

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.facebook.ads.*
import com.sofit.adshelper.AdsHelper

object FacebookBanner {
    fun showFacebookBanner(activity: Activity, bannerContainer: RelativeLayout, context: Context) {
        lateinit var facebookAdView: AdView
        facebookAdView = AdView(activity, AdsHelper.FACEBOOK_BANNER_ID, AdSize.BANNER_HEIGHT_90)
        bannerContainer.addView(facebookAdView)
        facebookAdView.setAdListener(object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                Log.e(
                    "facebookBannerAd",
                    "onError-Banner failed: " + adError.errorMessage + " in " + activity.localClassName
                )
            }

            override fun onAdLoaded(ad: Ad) {
                Log.e("facebookBannerAd", "onAdLoaded" + " in " + activity.localClassName)
            }

            override fun onAdClicked(ad: Ad) {
                Log.e("facebookBannerAd", "onAdClicked")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.e("facebookBannerAd", "onLoggingImpression")
            }
        })
        facebookAdView.loadAd()
    }

}