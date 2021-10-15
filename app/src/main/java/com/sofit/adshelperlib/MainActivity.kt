package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.core.AdsHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AdsHelper.showMoPubBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.loadMoPubInterstitial(this@MainActivity)
        AdsHelper.showMoPubNativeAd(this@MainActivity, findViewById(R.id.moPubNativeView))
    }

    fun showFBAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity)
    }

    fun showAdMobAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity)
    }

    fun showMoPubAds(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity)
    }

}