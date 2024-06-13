package com.talkable.presentation.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemMyPageCalendarBinding
import com.talkable.presentation.mypage.model.CalendarModel
import com.talkable.presentation.mypage.viewholder.MyPageCalendarViewHolder

class MyPageCalendarAdapter(context: Context) :
    ListAdapter<CalendarModel, MyPageCalendarViewHolder>(
        calendarDiffCallback
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageCalendarViewHolder {
        val binding =
            ItemMyPageCalendarBinding.inflate(inflater, parent, false)
        return MyPageCalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageCalendarViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val calendarDiffCallback =
            ItemDiffCallback<CalendarModel>(onItemsTheSame = { old, new -> old.date == new.date },
                onContentsTheSame = { old, new -> old == new })
    }
}