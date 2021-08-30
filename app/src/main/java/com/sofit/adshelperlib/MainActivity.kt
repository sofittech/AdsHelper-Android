package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.core.AdsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AdsHelper.showBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.showMediationTestSuite(this)
        AdsHelper.showNative(this, findViewById(R.id.my_template))
    }

    fun loadInterstitial(view: View) {
        AdsHelper.loadInterstitial(this@MainActivity)
    }

    fun showInterstitial(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity)
    }
}