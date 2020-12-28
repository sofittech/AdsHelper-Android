@file:Suppress("DEPRECATION")

package com.sofit.adshelper.allAds

import android.content.Context
import android.util.Log
import com.sofit.adshelper.BuildConfig
import java.util.*

object  UtilClass {
    fun verifyInstallerId(context: Context): Boolean {
        return if (BuildConfig.DEBUG) {
            true
        } else {
            val validInstallers: List<String> = ArrayList(listOf("com.android.vending"))
            val installer = context.packageManager.getInstallerPackageName(context.packageName)
            // alternative is context.packageManager.packageInstaller but not sure it will work or not that why suppressed the Deprecation
            Log.e("intallerCheck",installer.toString())
            installer != null && validInstallers.contains(installer)
        }
    }


}