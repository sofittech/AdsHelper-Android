package com.sofit.adshelper.allAds

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubView
import com.sofit.adshelper.core.AdsHelper

object MoPubBanner {
    private lateinit var moPubViewBannerAdListener: MoPubView.BannerAdListener

    fun showMoPubBanner(activity: Activity, adMobContainer: RelativeLayout) {
        Handler(Looper.getMainLooper()).postDelayed({
            initAd(activity, adMobContainer)
        }, 1000)

    }

    private fun initAd(activity: Activity, adMobContainer: RelativeLayout) {
        val moPubBanner = MoPubView(activity)
        val adParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        adParams.addRule(RelativeLayout.CENTER_IN_PARENT, 1) //to align horizontally at center

        moPubBanner.setAdUnitId(AdsHelper.moPubBannerId)
        adMobContainer.addView(moPubBanner, adParams)

        Log.e("mopubAd", "Banner Ad Loading...")

        moPubViewBannerAdListener = object : MoPubView.BannerAdListener {
            override fun onBannerExpanded(banner: MoPubView) {
                Log.e("mopubAd", "Expanded")
            }

            override fun onBannerLoaded(banner: MoPubView) {
                Log.e("mopubAd", "Banner ad has loaded")
                adMobContainer.isVisible = true
            }

            override fun onBannerCollapsed(banner: MoPubView) {
                Log.e("mopubAd", "Banner ad has collapsed")
            }

            override fun onBannerFailed(banner: MoPubView, errorCode: MoPubErrorCode) {
                Log.e("mopubAd", "Banner ad failed to load with error:$errorCode")
            }

            override fun onBannerClicked(banner: MoPubView?) {
                Log.e("mopubAd", "Banner ad was clicked")
            }
        }
        moPubBanner.bannerAdListener = moPubViewBannerAdListener
        moPubBanner.loadAd()
    }
}