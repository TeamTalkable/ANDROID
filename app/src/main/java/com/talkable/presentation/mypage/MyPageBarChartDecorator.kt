package com.talkable.presentation.mypage

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.talkable.core.util.context.pxToDp

class MyPageBarChartDecorator(val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == parent.adapter?.itemCount?.minus(1)) {
            view.updatePadding(right = context.pxToDp(32))
        }
    }
}