package com.raafat.timesegmentsprogress

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
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

        timeSegmentView.addTimeSheet("12h:04m",Color.BLACK)
        timeSegmentView.addTimeSheet("02h:34m",Color.RED)
        timeSegmentView.addTimeSheet("<1m",Color.BLUE)
    }
}