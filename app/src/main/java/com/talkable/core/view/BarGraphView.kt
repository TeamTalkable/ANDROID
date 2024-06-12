package com.talkable.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.talkable.R
import com.talkable.core.util.context.colorOf

class BarGraphView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0
    private var maxValue = 100
    private val paint = Paint()
    private val rect = RectF()

    fun setProgress(value: Int, maxValue: Int) {
        progress = value
        this.maxValue = maxValue
        paint.color = if (value <= maxValue.div(2)) context.colorOf(R.color.main_1) else
            context.colorOf(R.color.main)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (maxValue > 0) {
            val calculatedHeight = height * progress / maxValue
            rect.left = 0f
            rect.top = (this.height - calculatedHeight).toFloat()
            rect.right = width.toFloat()
            rect.bottom = this.height.toFloat()
            canvas.drawRoundRect(rect, 40f, 40f, paint)
        }
    }
}
