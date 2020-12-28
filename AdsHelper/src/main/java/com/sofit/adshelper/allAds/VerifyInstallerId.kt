package com.sofit.adshelper.allAds

import android.content.Context
import com.sofit.adshelper.BuildConfig
import java.util.*

object  UtilClass {
    fun verifyinstallerid(context: Context): Boolean {
        return if (BuildConfig.DEBUG) {
            true
        } else {
            val validInstallers: List<String> = ArrayList(Arrays.asList("com.android.vending"))
            val installer = context.packageManager.getInstallerPackageName(context.packageName)
            installer != null && validInstallers.contains(installer)
        }
    }


}