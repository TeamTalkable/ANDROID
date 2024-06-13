package com.talkable.presentation.mypage.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyPageCalendarBinding
import com.talkable.presentation.mypage.model.CalendarModel

class MyPageCalendarViewHolder(
    private val binding: ItemMyPageCalendarBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CalendarModel) = with(binding) {
        model = data
        executePendingBindings()
    }
}