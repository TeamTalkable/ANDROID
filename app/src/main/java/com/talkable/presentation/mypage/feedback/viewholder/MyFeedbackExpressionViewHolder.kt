package com.talkable.presentation.mypage.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackExpressionBinding
import com.talkable.presentation.mypage.feedback.data.Expression

class MyFeedbackExpressionViewHolder(
    private val binding: ItemMyFeedbackExpressionBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Expression) {
        binding.tvTalkFeedbackLearnedExpressionEnglish.text = data.wordEnglish
        binding.tvTalkFeedbackLearnedExpressionKorean.text = data.wordKorean
    }
}