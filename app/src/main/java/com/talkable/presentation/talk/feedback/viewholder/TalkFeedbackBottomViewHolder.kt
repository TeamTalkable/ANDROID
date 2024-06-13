package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackBottomBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkFeedbackBottomViewHolder(
    private val binding: ItemTalkFeedbackBottomBinding,
    private val onClickNavigateToMyPage: () -> Unit,
    private val onClickNavigateToHome: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvTalkFeedbackBottomGraph.setOnClickListener {
                onClickNavigateToMyPage()
            }

            binding.btnTalkFeedbackBottomComplete.setOnClickListener {
                onClickNavigateToHome()
            }
        }

    fun onBind(data: TalkFeedbackModel) = with(binding) {
        model = data
        executePendingBindings()
    }
}