package com.talkable.presentation.talk.feedback.viewholder

import android.media.MediaPlayer
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemTalkFeedbackLearnedPronunciationBinding
import com.talkable.presentation.talk.feedback.model.Learned

class TalkFeedbackLearnedPronunciationViewHolder(private val binding: ItemTalkFeedbackLearnedPronunciationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        val mediaSciencePlayer = MediaPlayer.create(itemView.context, R.raw.science)
        val mediaTodayPlayer = MediaPlayer.create(itemView.context, R.raw.today)
        val mediaScienceUserPlayer = MediaPlayer.create(itemView.context, R.raw.science_user)
        val mediaTodayUserPlayer = MediaPlayer.create(itemView.context, R.raw.today_user)
        with(binding) {
            ivTalkLearnedPronunciationAiVoice.setOnClickListener {
                ivTalkLearnedPronunciationAiVoice.isSelected =
                    !ivTalkLearnedPronunciationAiVoice.isSelected
                //임시 데이터
                val mediaPlayer = if (tvTalkLearnedPronunciationWord.text == "science") {
                    mediaSciencePlayer
                } else {
                    mediaTodayPlayer
                }

                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    ivTalkLearnedPronunciationAiVoice.isSelected = false
                }

            }
            ivTalkLearnedPronunciationUserVoice.setOnClickListener {
                ivTalkLearnedPronunciationUserVoice.isSelected =
                    !ivTalkLearnedPronunciationUserVoice.isSelected
                //임시 데이터
                val mediaPlayer = if (tvTalkLearnedPronunciationWord.text == "science") {
                    mediaScienceUserPlayer
                } else {
                    mediaTodayUserPlayer
                }

                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    ivTalkLearnedPronunciationUserVoice.isSelected = false
                }
            }
        }
    }

    fun onBind(data: Learned.Pronunciation) = with(binding) {
        model = data
        executePendingBindings()
    }
}