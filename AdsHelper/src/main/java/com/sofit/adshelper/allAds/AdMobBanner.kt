package com.sofit.adshelper.allAds

import android.app.Activity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.google.android.ads.mediationtestsuite.MediationTestSuite
import com.google.android.gms.ads.*
import com.sofit.adshelper.core.AdsHelper

object AdMobBanner {

    fun showAdMobBanner(activity: Activity, adMobContainer: RelativeLayout) {
        val mAdView = AdView(activity)
        mAdView.adSize = getAdSize(activity)
        mAdView.adUnitId = AdsHelper.adMobBannerId
        adMobContainer.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adMobContainer.visibility = View.VISIBLE
//                val layoutDescription = RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT
//                )
                // adMobContainer.layoutParams = layoutDescription
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

    private fun getAdSize(activity: Activity): AdSize? {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }
}