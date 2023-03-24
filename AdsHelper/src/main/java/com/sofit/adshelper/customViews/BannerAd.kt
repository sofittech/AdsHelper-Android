package com.sofit.adshelper.customViews

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.google.android.gms.ads.*
import com.sofit.adshelper.R
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelper.enums.AdNetwork

class BannerAd(context: Context, attrs: AttributeSet?, adNetwork: AdNetwork) :
    RelativeLayout(context, attrs) {
    var setTopBorders: Boolean = false
    var setBottomBorder: Boolean = false
    var topView: View
    var bottomView: View
    var bannerMain: RelativeLayout? = null

    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.content_view, this)
        topView = view.findViewById(R.id.topBorder)
        bottomView = view.findViewById(R.id.bottomBorder)
        bannerMain = view.findViewById(R.id.bannerMain)
        val a: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MyAdView, 0, 0)

        try {
            setTopBorders = a.getBoolean(R.styleable.MyAdView_setTopBorder, false)
            setBottomBorder = a.getBoolean(R.styleable.MyAdView_setBottomBorder, false)

            if (setTopBorders) {
                topView.visibility = View.VISIBLE
            } else {
                topView.visibility = View.GONE
            }
            if (setBottomBorder) {

                bottomView.visibility = View.VISIBLE
            } else {
                bottomView.visibility = View.GONE
            }
            showAdMobBannerAds(context)
        } finally {
            a.recycle()
        }
    }

    private fun showAdMobBannerAds(activity: Context) {
        val mAdView = AdView(activity)
        val adsSize = adSize
        adsSize?.let { mAdView.setAdSize(it) }
        AdsHelper.adMobBannerId?.let {
            mAdView.adUnitId = it
        }
        bannerMain?.addView(mAdView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                bannerMain?.visibility = View.VISIBLE
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

    private val adSize: AdSize?
        get() {
            val display = (context as Activity).windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = bannerMain?.width?.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels?.div(density))?.toInt()

            return adWidth?.let {
                AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                    context,
                    it
                )
            }
        }


}