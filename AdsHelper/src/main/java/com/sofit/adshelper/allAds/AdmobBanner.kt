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
import javax.security.auth.login.LoginException

object AdmobBanner {

    fun showAdmobBanner(activity: Activity, adMobContainer: RelativeLayout, context: Context) {
        val mAdView = AdView(activity)
        mAdView.adSize = getAdSize(activity, context)
        mAdView.adUnitId = AdsHelper.ADMOB_BANNER_ID
        adMobContainer.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.e("admobBanner: ","admob Banner loaded")
                // Code to be executed when an ad finishes loading.
                val layout_description = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                adMobContainer.layoutParams = layout_description
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e("admobBanner: ","admob Banner Failed")

                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                Log.e("admobBanner: ","admob Banner Opened")

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                Log.e("admobBanner: ","admob Banner Clicked")

                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                Log.e("admobBanner: ","admob Banner Left Application")

                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                Log.e("admobBanner: ","admob Banner Closed")

                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
        mAdView.loadAd(adRequest)
    }

    fun getAdSize(activity: Activity, context: Context): AdSize? {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display: Display = activity.getWindowManager().getDefaultDisplay()
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }
}