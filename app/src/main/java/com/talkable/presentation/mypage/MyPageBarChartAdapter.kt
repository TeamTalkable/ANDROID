package com.talkable.presentation.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemMyPageBarChartBinding
import com.talkable.presentation.mypage.model.BarChart
import com.talkable.presentation.mypage.viewholder.MyPageBarChartViewHolder

class MyPageBarChartAdapter(private val context: Context, private val maxStudyTime: Int) :
    ListAdapter<BarChart, MyPageBarChartViewHolder>(
        barChartDiffCallback
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageBarChartViewHolder {
        val binding =
            ItemMyPageBarChartBinding.inflate(inflater, parent, false)
        return MyPageBarChartViewHolder(context, binding, maxStudyTime)
    }

    override fun onBindViewHolder(holder: MyPageBarChartViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val barChartDiffCallback =
            ItemDiffCallback<BarChart>(onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new })
    }
}