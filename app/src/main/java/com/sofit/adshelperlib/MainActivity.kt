package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.core.AdsHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AdsHelper.showFacebookBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.loadFacebookInterstitial()
        AdsHelper.loadFacebookInterstitial()
    }

    fun showFBAd(view: View) {
        AdsHelper.showFacebookInterstitial()
    }
}