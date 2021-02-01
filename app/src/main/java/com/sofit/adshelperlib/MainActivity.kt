package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.mainclass.AdsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AdsHelper.showAdMobBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.showFacebookBanner(this@MainActivity, findViewById(R.id.bannerFacebook))
        AdsHelper.loadFacebookInterstitial(false)
        AdsHelper.loadAdMobInterstitial(false)
    }

    fun showFBAd(view: View) {
        AdsHelper.showFacebookInterstitial(this@MainActivity)
    }

    fun showAdMobAd(view: View) {
        AdsHelper.showAdMobInterstitial(this@MainActivity)
    }

}