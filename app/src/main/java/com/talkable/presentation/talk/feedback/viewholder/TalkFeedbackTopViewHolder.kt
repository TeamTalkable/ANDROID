package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.R
import com.talkable.databinding.ItemTalkFeedbackTopBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkFeedbackTopViewHolder(private val binding: ItemTalkFeedbackTopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: TalkFeedbackModel) = with(binding) {
        model = data
        executePendingBindings()
        //bindingadapter 곧 추가하고 바꿀거임
        binding.ivTalkFeedbackTopFlower.setImageResource(R.drawable.background_splash_screen)
    }
}