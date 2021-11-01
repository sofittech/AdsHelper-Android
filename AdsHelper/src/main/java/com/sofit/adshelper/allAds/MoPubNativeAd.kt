package com.sofit.adshelper.allAds

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.mopub.nativeads.*
import com.sofit.adshelper.core.AdsHelper
import com.mopub.nativeads.FacebookAdRenderer
import com.mopub.nativeads.FacebookAdRenderer.FacebookViewBinder


object MoPubNativeAd {
    private lateinit var moPubNativeNetworkListener: MoPubNative.MoPubNativeNetworkListener
    private lateinit var moPubNativeEventListener: NativeAd.MoPubNativeEventListener
    private lateinit var moPubNative: MoPubNative
    private lateinit var adapterHelper: AdapterHelper

    fun loadAd(activity: Activity, nativeView: FrameLayout) {
        Handler(Looper.getMainLooper()).postDelayed({
            loadMoPubBanner(activity, nativeView)
        }, 100)
    }

    private fun loadMoPubBanner(activity: Activity, nativeView: FrameLayout) {
        moPubNativeEventListener = object : NativeAd.MoPubNativeEventListener {
            override fun onClick(view: View?) {
                // Click tracking.
                Log.e("mopubAd", "Native ad recorded a click")
            }

            override fun onImpression(view: View?) {
                // Impress is recorded - do what is needed AFTER the ad is visibly shown here.
                Log.e("mopubAd", "Native ad recorded an impression")
            }

        }

        moPubNativeNetworkListener = object : MoPubNative.MoPubNativeNetworkListener {
            override fun onNativeLoad(nativeAd: NativeAd?) {
                adapterHelper = AdapterHelper(activity, 0, 3)
                val v = adapterHelper.getAdView(
                    null, nativeView, nativeAd,
                    ViewBinder.Builder(0).build()
                )
                nativeAd!!.setMoPubNativeEventListener(moPubNativeEventListener)
                nativeView.addView(v)
                Log.e("mopubAd", "Native ad has loaded")
                nativeView.isVisible = true
            }

            override fun onNativeFail(errorCode: NativeErrorCode) {
                Log.e("mopubAd", "Native ad failed to load with error: $errorCode")
            }
        }

        moPubNative = MoPubNative(activity, AdsHelper.moPubNativeID, moPubNativeNetworkListener)

        val viewBinder = ViewBinder.Builder(com.sofit.adshelper.R.layout.mopub_native_ad_view)
            .titleId(com.sofit.adshelper.R.id.mopub_native_ad_title)
            .textId(com.sofit.adshelper.R.id.mopub_native_ad_text)
            .mainImageId(com.sofit.adshelper.R.id.mopub_native_ad_main_imageview)
            .iconImageId(com.sofit.adshelper.R.id.mopub_native_ad_icon)
            .callToActionId(com.sofit.adshelper.R.id.mopub_native_ad_cta)
            .privacyInformationIconImageId(com.sofit.adshelper.R.id.mopub_native_ad_privacy)
            .build()

        val facebookAdRenderer = FacebookAdRenderer(
            FacebookViewBinder.Builder(com.sofit.adshelper.R.layout.facebooknative)
                .titleId(com.sofit.adshelper.R.id.fb_native_ad_title)
                .textId(com.sofit.adshelper.R.id.fb_native_ad_body)
                .mediaViewId(com.sofit.adshelper.R.id.fb_native_ad_media)
                .adIconViewId(com.sofit.adshelper.R.id.fb_nativeIcon)
                .adChoicesRelativeLayoutId(com.sofit.adshelper.R.id.fb_ad_choices_container)
                .advertiserNameId(com.sofit.adshelper.R.id.fb_native_ad_title) // Bind either the titleId or advertiserNameId depending on the FB SDK version
                // End of binding to new layouts
                .callToActionId(com.sofit.adshelper.R.id.fb_native_ad_call_to_action)
                .build())
        moPubNative.registerAdRenderer(facebookAdRenderer)
        val adRenderer = MoPubStaticNativeAdRenderer(viewBinder)
        moPubNative.registerAdRenderer(adRenderer)
        moPubNative.makeRequest()
        Log.e("mopubAd", "Native ad is loading")



    }

}