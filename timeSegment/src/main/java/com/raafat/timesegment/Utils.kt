package com.raafat.timesegment

import android.content.Context
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.TypedValue


/**
 * Created by Raafat Alhmidi on 5/22/20 @4:51 PM.
 */


fun spToPx(sp: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    )
}

fun dpToPx(dp: Float, context: Context): Float {
    return dp * (context.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

}

fun pxToDp(px: Float, context: Context): Float {
    return px / (context.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

}

fun dpToSp(dp: Float, context: Context): Float {
    return (dpToPx(dp, context) / context.resources
        .displayMetrics.scaledDensity)
}

fun determineMaxTextSize(str: String, maxWidth: Float): Int {
    var size = 0
    val paint = Paint()
    do {
        paint.textSize = (++size).toFloat()
    } while (paint.measureText(str) < maxWidth)
    return size
} //End getMaxTextSize()

