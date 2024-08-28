package com.talkable.presentation.mypage.feedback

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.core.util.context.pxToDp

class MyFeedbackDecorator(private val context: Context) : RecyclerView.ItemDecoration() {

    private val dividerPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.font_5)
        strokeWidth = context.pxToDp(1).toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = context.pxToDp(20)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val left = parent.paddingStart.toFloat()
        val right = (parent.width - parent.paddingEnd).toFloat()

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin.toFloat()
            val bottom = top + dividerPaint.strokeWidth

            c.drawRect(left, top, right, bottom, dividerPaint)
        }
    }
}