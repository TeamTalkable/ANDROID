package com.talkable.presentation.mypage.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.talkable.databinding.ItemMyPageCalendarBinding
import com.talkable.presentation.mypage.model.CalendarModel

class MyPageCalendarViewHolder(
    private val binding: ItemMyPageCalendarBinding,
    private val onClickDate: (String, Int) -> Unit,
    private val viewPager: ViewPager2,
    private val size: Int,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            val nextItem = if (viewPager.currentItem + 1 < size) {
                viewPager.currentItem + 1
            } else 0
            viewPager.currentItem = nextItem
        }
    }

    fun bind(data: CalendarModel) = with(binding) {
        model = data
        executePendingBindings()
    }
}