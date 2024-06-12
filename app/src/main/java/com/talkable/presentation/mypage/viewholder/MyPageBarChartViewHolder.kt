package com.talkable.presentation.mypage.viewholder

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.core.util.context.colorOf
import com.talkable.core.util.context.pxToDp
import com.talkable.databinding.ItemMyPageBarChartBinding
import com.talkable.presentation.mypage.model.BarChart

class MyPageBarChartViewHolder(
    private val context: Context,
    private val binding: ItemMyPageBarChartBinding,
    private val maxStudyTime: Int
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: BarChart) = with(binding) {
        model = data
        executePendingBindings()
        barGraphMyPage.setProgress(data.studyTime, maxStudyTime)

        initStudyTimeTextColor(data.studyTime)
        setStudyTimeTextTopMargin(data.studyTime)
    }

    private fun initStudyTimeTextColor(studyTime: Int) {
        binding.tvMyPageChartStudyTime.setTextColor(
            if (studyTime <= maxStudyTime.div(2)) context.colorOf(R.color.font_3) else
                context.colorOf(R.color.white)
        )
    }

    private fun setStudyTimeTextTopMargin(studyTime: Int) = with(binding) {
        barGraphMyPage.post {
            val calculatedHeight = barGraphMyPage.height * studyTime / maxStudyTime
            val layoutParams =
                tvMyPageChartStudyTime.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = (barGraphMyPage.height - calculatedHeight) + context.pxToDp(7)
            tvMyPageChartStudyTime.layoutParams = layoutParams
        }
    }
}