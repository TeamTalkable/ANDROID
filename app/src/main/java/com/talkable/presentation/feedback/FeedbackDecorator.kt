package com.talkable.presentation.feedback

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.core.util.context.pxToDp

class FeedbackDecorator(val context: Context) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if(view.background != null){
            outRect.bottom = context.pxToDp(12)
            view.setBackgroundResource(R.drawable.shape_white_fill_12_rect)
        }
    }
}