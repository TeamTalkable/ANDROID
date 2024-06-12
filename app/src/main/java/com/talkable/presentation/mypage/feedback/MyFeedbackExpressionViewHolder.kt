package com.talkable.presentation.mypage.feedback

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackExpressionBinding

class MyFeedbackExpressionViewHolder(
    private val binding: ItemMyFeedbackExpressionBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Expression) {
        binding.tvTalkFeedbackLearnedExpressionEnglish.text = data.wordEnglish
        binding.tvTalkFeedbackLearnedExpressionKorean.text = data.wordKorean
    }
}