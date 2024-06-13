package com.talkable.presentation.talk.feedback

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.databinding.ItemTalkFeedbackBottomBinding
import com.talkable.presentation.talk.feedback.TalkFeedbackTopAdapter.Companion.TalkFeedbackDiffCallback
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackBottomViewHolder

class TalkFeedbackBottomAdapter(
    context: Context,
    private val onClickNavigateToMyPage: () -> Unit,
    private val onClickNavigateToHome: () -> Unit
) :
    ListAdapter<TalkFeedbackModel, TalkFeedbackBottomViewHolder>(
        TalkFeedbackDiffCallback,
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TalkFeedbackBottomViewHolder {
        val binding =
            ItemTalkFeedbackBottomBinding.inflate(
                inflater,
                parent,
                false
            )
        return TalkFeedbackBottomViewHolder(binding, onClickNavigateToMyPage, onClickNavigateToHome)
    }

    override fun onBindViewHolder(
        holder: TalkFeedbackBottomViewHolder,
        position: Int,
    ) {
        holder.onBind(currentList[position])
    }
}