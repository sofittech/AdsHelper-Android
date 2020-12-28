package com.sofit.adshelper.allAds

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.sofit.adshelper.AdsHelper

object AdmobBanner {

    fun showAdmobBanner(activity: Activity, adMobContainer: RelativeLayout) {
        val mAdView = AdView(activity)
        mAdView.adSize = getAdSize(activity,activity)
        mAdView.adUnitId = AdsHelper.adMob_banner_id
        adMobContainer.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.e("adMobBanner: ", "adMob Banner loaded")
                // Code to be executed when an ad finishes loading.
                val layoutDescription = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                adMobContainer.layoutParams = layoutDescription
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e("adMobBanner: ", "adMob Banner Failed")

                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                Log.e("adMobBanner: ", "adMob Banner Opened")

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                Log.e("adMobBanner: ", "adMob Banner Clicked")

                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                Log.e("adMobBanner: ", "adMob Banner Left Application")

                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                Log.e("adMobBanner: ", "adMob Banner Closed")

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