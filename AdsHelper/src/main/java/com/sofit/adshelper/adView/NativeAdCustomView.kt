package com.sofit.adshelper.adView

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.sofit.adshelper.R

class NativeAdCustomView : FrameLayout {
    private var templateType = 0
    private lateinit var styles: AdmobNativeAdTemplateStyle
    private lateinit var nativeAd: NativeAd
    lateinit var nativeAdView: NativeAdView
        private set
    private lateinit var primaryView: TextView
    private lateinit var secondaryView: TextView
//    private lateinit var ratingBar: RatingBar
    private lateinit var tertiaryView: TextView
    private lateinit var iconView: ImageView
    private lateinit var mediaView: MediaView
    private lateinit var callToActionView: Button
    private lateinit var background: ConstraintLayout

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    fun setStyles(styles: AdmobNativeAdTemplateStyle) {
        this.styles = styles
        applyStyles()
    }

    private fun applyStyles() {
        val mainBackground: Drawable? = styles.mainBackgroundColor
        if (mainBackground != null) {
            background.background = mainBackground
            primaryView.background = mainBackground
            secondaryView.background = mainBackground
            tertiaryView.background = mainBackground
        }
        val primary = styles.primaryTextTypeface
        if (primary != null) {
            primaryView.typeface = primary
        }
        val secondary = styles.secondaryTextTypeface
        if (secondary != null) {
            secondaryView.typeface = secondary
        }
        val tertiary = styles.tertiaryTextTypeface
        if (tertiary != null) {
            tertiaryView.typeface = tertiary
        }
        val ctaTypeface = styles.callToActionTextTypeface
        if (ctaTypeface != null) {
            callToActionView.typeface = ctaTypeface
        }
        val primaryTypefaceColor = styles.primaryTextTypefaceColor
        if (primaryTypefaceColor > 0) {
            primaryView.setTextColor(primaryTypefaceColor)
        }
        val secondaryTypefaceColor = styles.secondaryTextTypefaceColor
        if (secondaryTypefaceColor > 0) {
            secondaryView.setTextColor(secondaryTypefaceColor)
        }
        val tertiaryTypefaceColor = styles.tertiaryTextTypefaceColor
        if (tertiaryTypefaceColor > 0) {
            tertiaryView.setTextColor(tertiaryTypefaceColor)
        }
        val ctaTypefaceColor = styles.callToActionTypefaceColor
        if (ctaTypefaceColor > 0) {
            callToActionView.setTextColor(ctaTypefaceColor)
        }
        val ctaTextSize = styles.callToActionTextSize
        if (ctaTextSize > 0) {
            callToActionView.textSize = ctaTextSize
        }
        val primaryTextSize = styles.primaryTextSize
        if (primaryTextSize > 0) {
            primaryView.textSize = primaryTextSize
        }
        val secondaryTextSize = styles.secondaryTextSize
        if (secondaryTextSize > 0) {
            secondaryView.textSize = secondaryTextSize
        }
        val tertiaryTextSize = styles.tertiaryTextSize
        if (tertiaryTextSize > 0) {
            tertiaryView.textSize = tertiaryTextSize
        }
        val ctaBackground: Drawable? = styles.callToActionBackgroundColor
        if (ctaBackground != null) {
            callToActionView.background = ctaBackground
        }
        val primaryBackground: Drawable? = styles.primaryTextBackgroundColor
        if (primaryBackground != null) {
            primaryView.background = primaryBackground
        }
        val secondaryBackground: Drawable? = styles.secondaryTextBackgroundColor
        if (secondaryBackground != null) {
            secondaryView.background = secondaryBackground
        }
        val tertiaryBackground: Drawable? = styles.tertiaryTextBackgroundColor
        if (tertiaryBackground != null) {
            tertiaryView.background = tertiaryBackground
        }
        invalidate()
        requestLayout()
    }

    private fun adHasOnlyStore(nativeAd: NativeAd): Boolean {
        val store = nativeAd.store
        val advertiser = nativeAd.advertiser
        return !TextUtils.isEmpty(store) && TextUtils.isEmpty(advertiser)
    }

    fun setNativeAd(nativeAd: NativeAd) {
        this.nativeAd = nativeAd
        val store = nativeAd.store
        val advertiser = nativeAd.advertiser
        val headline = nativeAd.headline
        val body = nativeAd.body
        val cta = nativeAd.callToAction
        val starRating = nativeAd.starRating
        val icon = nativeAd.icon
        val secondaryText: String
        nativeAdView.callToActionView = callToActionView
        nativeAdView.headlineView = primaryView
        if (templateTypeName == MEDIUM_TEMPLATE) {
            nativeAdView.mediaView = mediaView
            tertiaryView.text = body
            nativeAdView.bodyView = tertiaryView
        }
        secondaryView.visibility = VISIBLE
        if (adHasOnlyStore(nativeAd)) {
            nativeAdView.storeView = secondaryView
            secondaryText = store.toString()
        } else if (!TextUtils.isEmpty(advertiser)) {
            nativeAdView.advertiserView = secondaryView
            secondaryText = advertiser.toString()
        } else {
            secondaryText = ""
        }
        primaryView.text = headline
        callToActionView.text = cta
        if (starRating != null && starRating > 0) {
            secondaryView.visibility = GONE
//            ratingBar.visibility = VISIBLE
//            ratingBar.max = 5
           // nativeAdView.starRatingView = ratingBar
        } else {
            secondaryView.text = secondaryText
            secondaryView.visibility = VISIBLE
          //  ratingBar.visibility = GONE
        }
        if (icon != null) {
            iconView.visibility = VISIBLE
            iconView.setImageDrawable(icon.drawable)
        } else {
            iconView.visibility = GONE
        }

        nativeAdView.setNativeAd(nativeAd)
    }

    fun destroyNativeAd() {
        nativeAd.destroy()
    }

    val templateTypeName: String
        get() {
            if (templateType == R.layout.gnt_medium_template_view) {
                return MEDIUM_TEMPLATE
            } else if (templateType == R.layout.gnt_small_template_view) {
                return SMALL_TEMPLATE
            }
            return ""
        }

    private fun initView(context: Context, attributeSet: AttributeSet?) {
        val attributes =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.NativeAdView, 0, 0)
        templateType = try {
            attributes.getResourceId(
                R.styleable.NativeAdView_gnt_template_type,
                R.layout.gnt_medium_template_view
            )
        } finally {
            attributes.recycle()
        }
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(templateType, this)
    }

    public override fun onFinishInflate() {
        super.onFinishInflate()
        nativeAdView = findViewById<View>(R.id.native_ad_view) as NativeAdView
        primaryView = findViewById<View>(R.id.primary) as TextView
        secondaryView = findViewById<View>(R.id.secondary) as TextView
        if (templateTypeName == MEDIUM_TEMPLATE) {
            tertiaryView = findViewById<View>(R.id.body) as TextView
            mediaView = findViewById<View>(R.id.media_view) as MediaView

        }
//        ratingBar = findViewById<View>(R.id.rating_bar) as RatingBar
//        ratingBar.isEnabled = false
        callToActionView = findViewById<View>(R.id.cta) as Button
        iconView = findViewById<View>(R.id.icon) as ImageView
        background = findViewById<View>(R.id.background) as ConstraintLayout
    }

    companion object {
        private const val MEDIUM_TEMPLATE = "medium_template"
        private const val SMALL_TEMPLATE = "small_template"
    }
}
