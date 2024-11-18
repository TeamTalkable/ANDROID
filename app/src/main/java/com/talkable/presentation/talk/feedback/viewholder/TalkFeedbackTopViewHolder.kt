package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.databinding.ItemTalkFeedbackTopBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import timber.log.Timber

class TalkFeedbackTopViewHolder(private val binding: ItemTalkFeedbackTopBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: TalkFeedbackModel) = with(binding) {
        model = data
        executePendingBindings()
        binding.ivTalkFeedbackTopFlower.load("https://github.com/user-attachments/assets/e5f50453-cb7c-40a0-8c3b-55b9c75ca770")
    }
}

