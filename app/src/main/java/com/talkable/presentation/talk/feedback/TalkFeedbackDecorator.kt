package com.talkable.presentation.talk.feedback

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.core.util.context.pxToDp

class TalkFeedbackDecorator(val context: Context, private val grammarPosition: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 2) {
            view.setBackgroundResource(R.drawable.shape_gray2_fill_top_12_rec)
            view.updatePadding(top = context.pxToDp(24))
        }

        if (position == grammarPosition) {
            view.setBackgroundResource(R.drawable.shape_gray2_fill_top_12_rec)
        }
    }
}