package com.talkable.presentation.mypage.feedback

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackGrammarBinding

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