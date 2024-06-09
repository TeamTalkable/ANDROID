package com.talkable.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import com.talkable.R
import com.talkable.presentation.mypage.model.Chart
import kotlin.math.cos
import kotlin.math.sin

class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val paintAfterFill = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintBeforeFill = Paint(Paint.ANTI_ALIAS_FLAG)

    private lateinit var userChartPercentage: Chart

    init {
        setCircleStyle()
        setFillCircleStyle(R.color.main_2, paintAfterFill)
        setFillCircleStyle(R.color.chart_before, paintBeforeFill)
    }

    fun setChartPercentage(percentage: Chart) {
        userChartPercentage = percentage
        invalidate()
    }

    private fun setCircleStyle() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f
//        paint.color = context.colorOf(R.color.font_4)
    }

    private fun setFillCircleStyle(@ColorRes resId: Int, paint: Paint) {
        paint.style = Paint.Style.FILL
//        paint.color = context.colorOf(resId)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        val cx = (width / 2).toFloat()
        val cy = (height / 2).toFloat()
        val radius = width.coerceAtMost(height) / 2 - 40

        with(userChartPercentage) {
            if (!userChartPercentage.isEmpty) {
                drawUserChart(
                    cx,
                    cy,
                    radius,
                    canvas,
                    paintAfterFill,
                    afterExpressionMeasure.toFloat().div(100),
                    afterGrammarMeasure.toFloat().div(100),
                    afterPronunciationMeasure.toFloat().div(100),
                    afterListeningMeasure.toFloat().div(100)
                )
                drawUserChart(
                    cx,
                    cy,
                    radius,
                    canvas,
                    paintBeforeFill,
                    beforeExpressionMeasure.toFloat().div(100),
                    beforeGrammarMeasure.toFloat().div(100),
                    beforePronunciationMeasure.toFloat().div(100),
                    beforeListeningMeasure.toFloat().div(100)
                )
            }
        }

        drawCircle(cx, cy, radius, canvas)
        drawSector(cx, cy, radius, canvas)
    }

    private fun drawCircle(cx: Float, cy: Float, radius: Int, canvas: Canvas) {
        for (i in 1..CIRCLE_MAX) {
            setCircleDash((radius * i / 6f), radius)
            canvas.drawCircle(cx, cy, radius * i / 6f, paint)
        }
    }

    private fun setCircleDash(currentRadius: Float, radius: Int) {
        val patternLength = 13f
        val dashLength = patternLength * (currentRadius / radius)
        val dashEffect = DashPathEffect(floatArrayOf(dashLength, dashLength), 15f)
        paint.pathEffect = dashEffect
    }

    private fun drawSector(cx: Float, cy: Float, radius: Int, canvas: Canvas) {
        val density = context.resources.displayMetrics.density

        val angle = 2 * Math.PI / 4
        for (i in 0 until SECTOR_MAX) {
            path.reset()
            path.moveTo(cx, cy)
            val endX = (cx + (radius + EXTENDED_LINE_LENGTH * density) * cos(i * angle)).toFloat()
            val endY = (cy + (radius + EXTENDED_LINE_LENGTH * density) * sin(i * angle)).toFloat()
            path.lineTo(endX, endY)
            path.arcTo(
                cx - radius,
                cy - radius,
                cx + radius,
                cy + radius,
                (Math.toDegrees(i * angle)).toFloat(),
                (Math.toDegrees(angle)).toFloat(),
                false
            )
            path.close()
            canvas.drawPath(path, paint)
        }
    }

    private fun drawUserChart(
        cx: Float, cy: Float, radius: Int, canvas: Canvas, paint: Paint,
        expressionPercentage: Float, grammarPercentage: Float,
        pronunciationPercentage: Float, listeningPercentage: Float,
    ) {
        drawArc(
            setRectF(cx, cy, expressionPercentage * radius),
            FIRST_PIECE_ANGLE,
            canvas,
            paint
        )
        drawArc(
            setRectF(cx, cy, grammarPercentage * radius),
            SECOND_PIECE_ANGLE,
            canvas,
            paint
        )
        drawArc(
            setRectF(cx, cy, pronunciationPercentage * radius),
            THIRD_PIECE_ANGLE,
            canvas,
            paint
        )
        drawArc(
            setRectF(cx, cy, (listeningPercentage * radius).toFloat()),
            FOURTH_PIECE_ANGLE,
            canvas,
            paint
        )
    }

    private fun setRectF(cx: Float, cy: Float, percentage: Float): RectF =
        RectF(cx - percentage, cy - percentage, cx + percentage, cy + percentage)


    private fun drawArc(rectF: RectF, startAngle: Float, canvas: Canvas, paint: Paint) =
        canvas.drawArc(rectF, startAngle, SWEEP_ANGLE, true, paint)


    companion object {
        const val EXTENDED_LINE_LENGTH = 30
        const val CIRCLE_MAX = 6
        const val SECTOR_MAX = 4
        const val SWEEP_ANGLE = 90f
        const val FIRST_PIECE_ANGLE = -90f
        const val SECOND_PIECE_ANGLE = 180f
        const val THIRD_PIECE_ANGLE = 90f
        const val FOURTH_PIECE_ANGLE = 0f
    }
}
