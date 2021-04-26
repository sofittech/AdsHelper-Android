package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelper.enums.AdNetwork

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AdsHelper.showFacebookBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.loadFacebookInterstitial(false)
     }

    fun showFBAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.Facebook)
    }

    fun showAdMobAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.AdMob)
    }

}