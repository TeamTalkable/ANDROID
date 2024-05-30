package com.talkable.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        init()
    }

    private fun init() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val radius = Math.min(width, height) / 2 - 40
        val cx = width / 2
        val cy = height / 2

        // 동심원 그리기
        paint.color = Color.LTGRAY
        for (i in 1..6) {
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius * i / 6f, paint)
        }

        // 4분원 섹터 그리기
        val angle = 2 * Math.PI / 4
        paint.color = Color.DKGRAY
        for (i in 0..3) {
            path.reset()
            path.moveTo(cx.toFloat(), cy.toFloat())
            path.lineTo((cx + radius * Math.cos(i * angle)).toFloat(), (cy + radius * Math.sin(i * angle)).toFloat())
            path.arcTo((cx - radius).toFloat(), (cy - radius).toFloat(), (cx + radius).toFloat(), (cy + radius).toFloat(), (Math.toDegrees(i * angle)).toFloat(), (Math.toDegrees(angle)).toFloat(), false)
            path.close()
            canvas.drawPath(path, paint)
        }
    }
}
