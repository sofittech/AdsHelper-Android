package com.sofit.adshelper.adView

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.sofit.adshelper.R

class MyAdView(context: Context, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    var setTopBorders: Boolean = false
    var setBottomBorder: Boolean = false
    var topView: View
    var bottomView: View

    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.content_view, this)
        topView = view.findViewById(R.id.topBorder)
        bottomView = view.findViewById(R.id.bottomBorder)
        val a: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MyAdView, 0, 0)

        try {
            setTopBorders = a.getBoolean(R.styleable.MyAdView_setTopBorder, false)
            setBottomBorder = a.getBoolean(R.styleable.MyAdView_setBottomBorder, false)

            if (setTopBorders) {
                topView.visibility = View.VISIBLE
            }else {
                topView.visibility= View.GONE
            }
            if (setBottomBorder) {

                bottomView.visibility = View.VISIBLE
            }else{
                bottomView.visibility=View.GONE
            }
        } finally {
            a.recycle()
        }
    }


}