package com.raafat.timesegment

import android.graphics.Paint
import androidx.annotation.ColorInt

/**
 * Created by Raafat Alhmidi on 7/9/20 @4:35 PM.
 */
data class TimeItem @JvmOverloads constructor(val startTime:Long =0, val endTime:Long, @ColorInt val color:Int) {

     fun getPaint():Paint = Paint().apply {
         isAntiAlias = true
         style  = Paint.Style.FILL
         color = this@TimeItem.color
     }

    fun getStartX():Float = startTime.toFloat()/(24*60*60*1000)
    fun getEndX():Float = endTime.toFloat()/(24*60*60*1000)

}