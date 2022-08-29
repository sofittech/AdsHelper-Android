package com.sofit.adshelperlib

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.core.AdsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AdsHelper.showBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.showMediationTestSuite(this)
    }

    fun loadInterstitial(view: View) {
        AdsHelper.loadNativeAd(this, true, findViewById(R.id.my_template))
        AdsHelper.loadInterstitial(
            this@MainActivity,
            getString(R.string.AdMob_test_id_interstitial)
        )
    }

    fun showInterstitial(view: View) {
//        AdsHelper.showNativeAd(frameLayout = findViewById(R.id.my_template))
        AdsHelper.showInterstitialAd(this@MainActivity) {
            Log.e("done", "showInterstitial: done")
        }
    }
}