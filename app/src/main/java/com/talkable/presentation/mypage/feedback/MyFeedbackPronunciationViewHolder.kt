package com.talkable.presentation.mypage.feedback

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackPronunciationBinding

class MyFeedbackPronunciationViewHolder(
    private val binding: ItemMyFeedbackPronunciationBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String) {
        with(binding) {
            tvMyFeedback.text = data
        }
    }
}