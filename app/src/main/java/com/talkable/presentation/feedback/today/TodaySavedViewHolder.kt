package com.talkable.presentation.feedback.today

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTodaySavedBinding
import com.talkable.presentation.feedback.today.model.ItemType
import com.talkable.presentation.feedback.today.model.TodaySaved

class TodaySavedViewHolder(private val binding: ItemTodaySavedBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodaySaved, isLastItem: Boolean) {
        binding.apply {
            if (item.type == ItemType.WORD) {
                tvTodaySavedEnglish.text = item.word
                tvTodaySavedKorean.text = item.translation
            } else if (item.type == ItemType.SENTENCE) {
                tvTodaySavedEnglish.text = item.sentence
                tvTodaySavedKorean.text = item.translation
            }

            dividerTodaySaved.visibility = if (isLastItem) GONE else VISIBLE
        }
    }

    companion object {
        fun from(parent: ViewGroup): TodaySavedViewHolder {
            val binding =
                ItemTodaySavedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return TodaySavedViewHolder(binding)
        }
    }
}