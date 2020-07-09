package com.raafat.timesegment

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
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

fun setTextSizeForWidth(
    paint: Paint, desiredWidth: Float,
    text: String
) {

    // Pick a reasonably large value for the test. Larger values produce
    // more accurate results, but may cause problems with hardware
    // acceleration. But there are workarounds for that, too; refer to
    // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
    val testTextSize = 28f

    // Get the bounds of the text, using our testTextSize.
    paint.textSize = testTextSize
    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)

    // Calculate the desired size as a proportion of our testTextSize.
    val desiredTextSize: Float = testTextSize * desiredWidth / bounds.width()

    // Set the paint for that size.
    paint.textSize = desiredTextSize
}

