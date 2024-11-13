package com.talkable.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemTalkReviewBinding
import com.talkable.presentation.home.viewholder.TalkReviewTopViewHolder
import com.talkable.presentation.talk.feedback.model.TalkFeedbackModel

class TalkReviewTopAdapter(context: Context) :
    ListAdapter<TalkFeedbackModel, TalkReviewTopViewHolder>(
        TalkFeedbackDiffCallback,
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TalkReviewTopViewHolder {
        val binding =
            ItemTalkReviewBinding.inflate(inflater, parent, false)
        return TalkReviewTopViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TalkReviewTopViewHolder,
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