package com.sofit.adshelper.allAds

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.widget.RelativeLayout
import com.google.android.gms.ads.*
import com.sofit.adshelper.core.AdsHelper
import timber.log.Timber

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
                Timber.e("AdMob Banner Loaded")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Timber.e("AdMob Banner: ${loadAdError.message}")
            }

            override fun onAdOpened() {
            }

            override fun onAdClicked() {
            }

            override fun onAdClosed() {
            }
        }
        mAdView.loadAd(adRequest)
    }

    private fun getAdSize(activity: Activity): AdSize? {
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }
}