package com.talkable.presentation.mypage.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackPronunciationBinding

class MyFeedbackPronunciationViewHolder(
    private val binding: ItemMyFeedbackPronunciationBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String) {
        with(binding) {
            tvMyFeedbackPronunciation.text = data
        }
    }
}