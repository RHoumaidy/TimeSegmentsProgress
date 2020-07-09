package com.raafat.timesegment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

/**
 * Created by Raafat Alhmidi on 7/9/20 @4:33 PM.
 */
class TimeSegmentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val sdf = SimpleDateFormat("h a", Locale.US)
    private val sdf2 = SimpleDateFormat("hh:mm a", Locale.US)

    private var path = Path()
    private val bounds = Rect()

    private var backgroundPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }
    private var linePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
    }
    private var textPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
    }
    var times: ArrayList<TimeItem> = ArrayList()
        set(value) {
            field = value
            invalidate()
        }

    private var cornersRadius = 0.0f
        set(value) {
            field = value
            invalidate()
        }
    private var segmentCornersRadius = 0.0f
        set(value) {
            field = min(value, cornersRadius)
            invalidate()
        }

    private var textSize = 12f
        set(value) {
            field = value
            setTextSizeForWidth(textPaint, value, "M")
            invalidate()

        }
    private var background_color: Int = 0
        set(value) {
            field = value
            backgroundPaint.apply {
                color = background_color
            }
            invalidate()
        }
    private var textColor: Int = 0
        set(value) {
            field = value
            textPaint.apply {
                color = value
            }
            invalidate()
        }

    private var lineColor: Int = 0
        set(value) {
            field = value
            linePaint.apply {
                color = value
            }
            invalidate()
        }

    private var drawLines = true
        set(value) {
            field = value
            invalidate()
        }

    private var textHeight = 0

    init {
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        sdf2.timeZone = TimeZone.getTimeZone("GMT")

        context.withStyledAttributes(attrs, R.styleable.TimeSegmentView) {
            cornersRadius = getDimension(R.styleable.TimeSegmentView_corners_radius, 0f)
            background_color = getColor(R.styleable.TimeSegmentView_background_color, Color.WHITE)
            textSize = getDimension(R.styleable.TimeSegmentView_text_size, 12f)
            drawLines = getBoolean(R.styleable.TimeSegmentView_draw_lines, false)
            lineColor = getColor(R.styleable.TimeSegmentView_line_color, Color.GRAY)
            textColor = getColor(R.styleable.TimeSegmentView_text_color, Color.BLACK)

            segmentCornersRadius =
                getDimension(R.styleable.TimeSegmentView_segment_corners_radius, 0.0f)
        }

        textPaint.getTextBounds("12 AM", 0, "12 AM".length, bounds)
        textHeight = bounds.height() + 5
        path.reset()
        path.addRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat() - textHeight,
            cornersRadius,
            cornersRadius,
            Path.Direction.CW
        )
        path.close()
    }


    private var labels: ArrayList<Long> = arrayListOf(
        sdf.parse("2 AM")?.time ?: 0,
        sdf.parse("4 AM")?.time ?: 0,
        sdf.parse("6 AM")?.time ?: 0,
        sdf.parse("8 AM")?.time ?: 0,
        sdf.parse("10 AM")?.time ?: 0,
        sdf.parse("12 PM")?.time ?: 0,
        sdf.parse("2 PM")?.time ?: 0,
        sdf.parse("4 PM")?.time ?: 0,
        sdf.parse("6 PM")?.time ?: 0,
        sdf.parse("8 PM")?.time ?: 0,
        sdf.parse("10 PM")?.time ?: 0

    )

    fun addItem(timeItem: TimeItem) {
        times.add(timeItem)
        invalidate()
    }

    fun addItem(start: Long, end: Long, @ColorInt color: Int) {
        addItem(TimeItem(start, end, color))
    }

    fun clear(){
        times.clear()
        invalidate()
    }

    fun addItem(
        start: String,
        end: String,
        @ColorInt color: Int,
        simpleDateFormat: SimpleDateFormat
    ) {
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        addItem(
            simpleDateFormat.parse(start)?.time ?: 0,
            simpleDateFormat.parse(end)?.time ?: 0,
            color
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        textPaint.getTextBounds("12 AM", 0, "12 AM".length, bounds)
        textHeight = bounds.height() + 5
        path.reset()
        path.addRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat() - textHeight,
            cornersRadius,
            cornersRadius,
            Path.Direction.CW
        )
        path.close()

        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            for (label in labels) {
                val text = sdf.format(label)
                textPaint.getTextBounds(text, 0, text.length, bounds)
                drawText(
                    text,
                    label.toFloat() / (24 * 60 * 60 * 1000) * width - bounds.width() / 2,
                    height.toFloat(),
                    textPaint
                )
            }
            clipPath(path)
            drawRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat() - textHeight,
                backgroundPaint
            )

            for (item in times) {
                drawRoundRect(
                    item.getStartX() * width,
                    0f,
                    item.getEndX() * width,
                    height.toFloat() - textHeight,
                    segmentCornersRadius,
                    segmentCornersRadius,
                    item.getPaint()
                )
            }

            if (drawLines)
                for (label in labels) {
                    val text = sdf.format(label)
                    textPaint.getTextBounds(text, 0, text.length, bounds)

                    drawLine(
                        label.toFloat() / (24 * 60 * 60 * 1000) * width,
                        0f,
                        label.toFloat() / (24 * 60 * 60 * 1000) * width,
                        height.toFloat() - textHeight,
                        linePaint
                    )
                }


        }
        super.onDraw(canvas)


    }


}