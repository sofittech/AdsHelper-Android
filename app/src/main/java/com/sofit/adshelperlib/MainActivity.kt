package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.AdsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AdsHelper.showAdmobBanner(this@MainActivity,findViewById(R.id.bannerMain),this)
        AdsHelper.showFacebookBanner(this@MainActivity,findViewById(R.id.bannerFacebook),this)
        AdsHelper.loadFacebookAd(false)
        AdsHelper.loadAdmobAd(false)
    }

    fun showFBAd(view: View) {
        AdsHelper.showFacebookAd(this@MainActivity)
    }

    fun showAdMobAd(view: View) {
        AdsHelper.showAdmobAd(this@MainActivity)
    }

    fun fbBannerClick(view: View) {}
    fun adMobBannerClick(view: View) {
    }
}