# Ads helper library

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 🇵🇰

AdsHelper Android is a light weight library that enables developers to integrate Ads in a fast and secure fashion. 
Currently this SDK supports *Google AdMob* and *Facebook Ads network*. Keeping coming back from amazing updates and new Ad networks.

# Integration:
Here we have an overview of how you can start integrating this SDK into your app:

##. Add the dependency in gradle (Module)   
    implementation 'com.github.sofittech:AdsHelper-Android:1.0.6'
    
##. If you need *Facebook-Only* Implementation, add the following dependency in gradle:
    implementation 'com.github.sofittech:AdsHelper-Android:1.0.6-FACEBOOK'


## 3.  Get jetpack service by adding in gradle (Project)  repository
    maven { url 'https://jitpack.io' }

##. Add meta data for admob in manifest with test id if not available 
    <meta-data
         android:name="com.google.android.gms.ads.APPLICATION_ID"
         android:value="@string/admob_app_id" />
         
##. Add Admob test-Id in strings class: 
    <string name="admob_app_id">ca-app-pub-3940256099942544~3347511713</string>

##. A sample builder for your application class:

   	private fun setAds() {
        if (BuildConfig.DEBUG) {
             AdsHelper.Builder(context = applicationContext)
                .with(applicationContext)
                .isVerified(BuildConfig.DEBUG)
                .fbInterstitialID(applicationContext.getString(R.string.fb_test_id_interstitial))
                .fbBannerId(applicationContext.getString(R.string.fb_test_id_banner))
                .build()

         } else if (UtilsID.verifyInstallerId(applicationContext)) {
             AdsHelper.Builder(context = applicationContext)
                .with(applicationContext)
                .isVerified(UtilsID.verifyInstallerId(applicationContext))
                .fbBannerId(applicationContext.getString(R.string.fb_id_banner))
                .fbInterstitialID(applicationContext.getString(R.string.fb_id_interstitial))
                .build()
    }
     }
    And build ads as required (AdMob, Facebook) and (Banner, Interstitial and native ad) in builder class as shown.


##. Verify Installer ID method (Checks if the app was installed directly from PlayStore) :

    fun verifyInstallerId(context: Context): Boolean {
       val validInstallers: List<String> =
       ArrayList(listOf("com.android.vending"))
       val installer = context.packageManager.getInstallerPackageName(context.packageName)
       return installer != null && validInstallers.contains(installer)
      }
      

##. AdHelper view for banner Ads:
    <com.sofit.adshelper.customViews.BannerAdView
         android:id="@+id/bannerAdContainer"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_alignParentBottom="true"
         android:visibility="invisible"
         app:setTopBorder="true" />


##. Load banner ad(Facebook or AdMob) in on Create of existing activity like:
    AdsHelper.showFacebookBanner(MainActivity.this, findViewById(R.id.bannerAdContainer)); //Facebook Banner
    AdsHelper.showAdMobBanner(MainActivity.this, findViewById(R.id.bannerView)); //Admob Banner


##. Loading facebook interstitial Ad:
    AdsHelper.loadFacebookInterstitial(false);
    // The parameter “false” in the above function ask for auto load ad next time (in 15 seconds)
    
##. Loading Admob interstitial Ad:
    AdsHelper.loadAdMobInterstitial(false)

##. Then show the interstitial Ad where you want like:
    AdsHelper.showInterstitialAd(MainActivity.this, AdNetwork.Facebook); 
    // The second argument will bb your preferred network type for the interstital ad.




                   


