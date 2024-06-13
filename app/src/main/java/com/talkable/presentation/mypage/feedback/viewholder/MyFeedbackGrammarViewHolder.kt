package com.talkable.presentation.mypage.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackGrammarBinding
import com.talkable.presentation.mypage.feedback.data.Grammar

class MyFeedbackGrammarViewHolder(
    private val binding: ItemMyFeedbackGrammarBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Grammar) {
        with(binding) {
            tvMyFeedbackGrammarCorrect.text = data.correctGrammar
            tvMyFeedbackGrammarWrong.text = data.wrongGrammar
            tvMyFeedbackGrammarReason.text = data.reason
        }
    }
}