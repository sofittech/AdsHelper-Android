package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelper.enums.AdNetwork

class MainActivity : AppCompatActivity() {
    // lateinit var nativeAdCustomView: NativeAdCustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // nativeAdCustomView=findViewById(R.id.my_template)

        // AdsHelper.showAdMobBanner(this@MainActivity, findViewById(R.id.bannerView))
        //AdsHelper.showFacebookBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.showMoPubBanner(this@MainActivity, findViewById(R.id.bannerView))
        AdsHelper.loadFacebookInterstitial()
        AdsHelper.loadAdMobInterstitial(this@MainActivity)
        AdsHelper.loadMoPubInterstitial(this@MainActivity)
        AdsHelper.showMoPubNativeAd(this@MainActivity,findViewById(R.id.moPubNativeView))
    }

    fun showFBAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.Facebook)
    }

    fun showAdMobAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.AdMob)
    }

    fun showMoPubAds(view: View) {AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.MoPub)}

}