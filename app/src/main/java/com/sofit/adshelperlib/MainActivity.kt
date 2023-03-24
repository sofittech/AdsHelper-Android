package com.sofit.adshelperlib

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sofit.adshelper.adView.NativeAdCustomView
import com.sofit.adshelper.core.AdsHelper
import com.sofit.adshelper.enums.AdNetwork

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // nativeAdCustomView=findViewById(R.id.my_template)

        AdsHelper.showNativeBannerAds(
            this@MainActivity,
            findViewById(R.id.bannerMain),
            AdNetwork.Facebook
        )
        AdsHelper.loadNativeInterstitialAds(this@MainActivity, AdNetwork.AppLovin)
        AdsHelper.loadNativeInterstitialAds(this@MainActivity, AdNetwork.Facebook)
        AdsHelper.showAdMobNativeAd(this, findViewById<NativeAdCustomView>(R.id.my_template))
    }

    fun showFBAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.Facebook) {
            Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show()
        }
    }

    fun showAdMobAd(view: View) {
        AdsHelper.showInterstitialAd(this@MainActivity, AdNetwork.AppLovin) {
        }
    }


    fun showNativeAd(view: View) {}

}