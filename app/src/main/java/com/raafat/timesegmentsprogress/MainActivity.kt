package com.raafat.timesegmentsprogress

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raafat.timesegment.TimeItem
import com.raafat.timesegment.TimeSegmentView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val sdf = SimpleDateFormat("hh:mm a", Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val timeSegmentView = findViewById<TimeSegmentView>(R.id.timeSegments)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        timeSegmentView.times = arrayListOf(
            TimeItem(
                sdf.parse("01:33 AM")?.time ?: 0,
                sdf.parse("04:05 AM")?.time ?: 0,
                Color.RED
            ),
            TimeItem(
                sdf.parse("01:33 PM")?.time ?: 0,
                sdf.parse("02:05 PM")?.time ?: 0,
                Color.YELLOW
            ),
            TimeItem(
                sdf.parse("02:05 PM")?.time ?: 0,
                sdf.parse("09:55 PM")?.time ?: 0,
                Color.BLUE
            )
        )
    }
}