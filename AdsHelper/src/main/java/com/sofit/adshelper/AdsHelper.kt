package com.sofit.adshelper

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.MobileAds

class AdsHelper {
    val TAG: String = "awesomeAppName";
    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun initAdmob(context: Context) {
        MobileAds.initialize(context) {
        }
    }
}