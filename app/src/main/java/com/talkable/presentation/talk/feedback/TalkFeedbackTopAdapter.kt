package com.talkable.presentation.talk.feedback

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemTalkFeedbackTopBinding
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackTopViewHolder

class TalkFeedbackTopAdapter(context: Context) :
    ListAdapter<TalkFeedbackModel, TalkFeedbackTopViewHolder>(
        TalkFeedbackDiffCallback,
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TalkFeedbackTopViewHolder {
        val binding =
            ItemTalkFeedbackTopBinding.inflate(inflater, parent, false)
        return TalkFeedbackTopViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TalkFeedbackTopViewHolder,
        position: Int,
    ) {
        holder.onBind(currentList[position])
    }

    companion object {
        val TalkFeedbackDiffCallback = ItemDiffCallback<TalkFeedbackModel>(
            onItemsTheSame = { old, new -> old.talkFeedbackId == new.talkFeedbackId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}