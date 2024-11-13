package com.talkable.presentation.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.talkable.core.util.context.pxToDp
import com.talkable.presentation.talk.feedback.model.Learned

class TalkReviewFeedbackDecorator(val context: Context, private val learnedItems: List<Learned>) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val item = learnedItems.getOrNull(position)

        if (item is Learned.AfterAnswer) {
            val margin = context.pxToDp(12)
            outRect.bottom = margin
        }
    }
}