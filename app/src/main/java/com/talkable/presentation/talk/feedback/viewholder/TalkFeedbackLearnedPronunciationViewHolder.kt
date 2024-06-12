package com.talkable.presentation.talk.feedback.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkFeedbackLearnedPronunciationBinding
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedPronunciationViewHolder(private val binding: ItemTalkFeedbackLearnedPronunciationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            ivTalkLearnedPronunciationAiVoice.setOnClickListener {
                ivTalkLearnedPronunciationAiVoice.isSelected =
                    !ivTalkLearnedPronunciationAiVoice.isSelected
            }

            ivTalkLearnedPronunciationUserVoice.setOnClickListener {
                ivTalkLearnedPronunciationUserVoice.isSelected =
                    !ivTalkLearnedPronunciationUserVoice.isSelected
            }
        }
    }

    fun onBind(data: Learned.Pronunciation) = with(binding) {
        model = data
        executePendingBindings()
    }
}