#                                Documentation of Ads helper library

AdsHelper Android is a light weight library that enables developers to integrate Ads in a fast and secure fashion. 
Currently this SDK supports Google AdMob and Facebook Ads network. Keeping coming back from amazing updates and new Ad networks.

# Integration:
Here we have an overview of how you can start integrating this SDK into your app:

## 1.	Add the dependency in gradle (Module)   
    implementation 'com.github.sofittech:AdsHelper-Android:1.0.3'


## 2.	Get jetpack service by adding in gradle (Project)  repository
    maven { url 'https://jitpack.io' }

## 3.	Add meta data for admob in manifest with test id if not available 
    <meta-data
         android:name="com.google.android.gms.ads.APPLICATION_ID"
         android:value="@string/admob_app_id" />
         
## 4. Add test Id in string class: 
    <string name="admob_app_id">ca-app-pub-3940256099942544~3347511713</string>

## 5.	Add builder to application class like:

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


## 6.	The utils class for Installer Id is :

    fun verifyInstallerId(context: Context): Boolean {
       val validInstallers: List<String> =
       ArrayList(listOf("com.android.vending"))
       val installer = context.packageManager.getInstallerPackageName(context.packageName)
       return installer != null && validInstallers.contains(installer)
      }
      

## 7.	 Use view for banner Ads:
    <com.sofit.adshelper.customViews.BannerAdView
         android:id="@+id/bannerAdContainer"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_alignParentBottom="true"
         android:visibility="invisible"
         app:setTopBorder="true" />


## 8.	Load banner ad(Facebook or AdMob) in on Create of existing activity like:
    AdsHelper.showFacebookBanner(MainActivity.this, findViewById(R.id.bannerAdContainer));


## 9.	Load interstitial Ad like:
    AdsHelper.loadFacebookInterstitial(false);
    // The parameter “false” in the above fun ask for auto load ad next time (in 15 seconds)

## 10. Then show the interstitial Ad where you want like:
    AdsHelper.showFacebookInterstitial(MainActivity.this);





                   

