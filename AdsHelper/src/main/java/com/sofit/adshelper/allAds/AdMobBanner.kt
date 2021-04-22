package com.sofit.adshelper.allAds

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.RelativeLayout
import com.google.android.gms.ads.*
import com.sofit.adshelper.core.AdsHelper

object AdMobBanner {

    fun showAdMobBanner(activity: Activity, adMobContainer: RelativeLayout) {
        val mAdView = AdView(activity)
        mAdView.adSize = getAdSize(activity, activity)
        mAdView.adUnitId = AdsHelper.adMobBannerId
        adMobContainer.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adMobContainer.visibility = View.VISIBLE
                Log.e("AdMobBanner: ", "AdMob Banner loaded")
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e("AdMobBanner: ", "AdMob Banner Failed")

                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                Log.e("AdMobBanner: ", "AdMob Banner Opened")

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                Log.e("AdMobBanner: ", "AdMob Banner Clicked")

                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                Log.e("AdMobBanner: ", "AdMob Banner Left Application")

                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                Log.e("AdMobBanner: ", "AdMob Banner Closed")

                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
        mAdView.loadAd(adRequest)
    }

    @Suppress("DEPRECATION")
    fun getAdSize(activity: Activity, context: Context): AdSize? {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display: Display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }
}