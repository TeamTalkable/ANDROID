package com.talkable.presentation.talk.feedback

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemTalkFeedbackLearnedAfterSentenceBinding
import com.talkable.databinding.ItemTalkFeedbackLearnedExpressionBinding
import com.talkable.databinding.ItemTalkFeedbackLearnedGrammarBinding
import com.talkable.databinding.ItemTalkFeedbackLearnedLabelBinding
import com.talkable.databinding.ItemTalkFeedbackLearnedPronunciationBinding
import com.talkable.presentation.talk.feedback.model.Learned
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackLearnedAfterAnswerViewHolder
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackLearnedExpressionViewHolder
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackLearnedGrammarViewHolder
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackLearnedLabelViewHolder
import com.talkable.presentation.talk.feedback.viewholder.TalkFeedbackLearnedPronunciationViewHolder

class TalkFeedbackLearnedAdapter(private val context: Context) : ListAdapter<Learned, ViewHolder>(
    TalkFeedbackLearnedDiffCallback
) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Learned.Expression -> VIEW_TYPE_EXPRESSION
            is Learned.Grammar -> VIEW_TYPE_GRAMMAR
            is Learned.Pronunciation -> VIEW_TYPE_PRONUNCIATION
            is Learned.AfterAnswer -> VIEW_TYPE_AFTER_ANSWER
            else -> VIEW_TYPE_LABEL
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EXPRESSION -> {
                val binding = ItemTalkFeedbackLearnedExpressionBinding.inflate(
                    inflater, parent, false
                )

                TalkFeedbackLearnedExpressionViewHolder(binding)
            }

            VIEW_TYPE_GRAMMAR -> {
                val binding = ItemTalkFeedbackLearnedGrammarBinding.inflate(
                    inflater, parent, false
                )

                TalkFeedbackLearnedGrammarViewHolder(binding)
            }

            VIEW_TYPE_PRONUNCIATION -> {
                val binding = ItemTalkFeedbackLearnedPronunciationBinding.inflate(
                    inflater, parent, false
                )

                TalkFeedbackLearnedPronunciationViewHolder(binding)
            }

            VIEW_TYPE_AFTER_ANSWER -> {
                val binding = ItemTalkFeedbackLearnedAfterSentenceBinding.inflate(
                    inflater, parent, false
                )

                TalkFeedbackLearnedAfterAnswerViewHolder(binding, context)
            }

            else -> {
                val binding = ItemTalkFeedbackLearnedLabelBinding.inflate(
                    inflater, parent, false
                )

                TalkFeedbackLearnedLabelViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (val data = currentList[position]) {
            is Learned.Expression -> (holder as? TalkFeedbackLearnedExpressionViewHolder)?.run {
                onBind(data)
            }

            is Learned.Grammar -> (holder as? TalkFeedbackLearnedGrammarViewHolder)?.run {
                onBind(data)
            }

            is Learned.Pronunciation -> (holder as? TalkFeedbackLearnedPronunciationViewHolder)?.run {
                onBind(data)
            }

            is Learned.AfterAnswer -> (holder as? TalkFeedbackLearnedAfterAnswerViewHolder)?.run {
                onBind(data)
            }

            is Learned.Label -> (holder as? TalkFeedbackLearnedLabelViewHolder)?.run { onBind(data) }
        }
    }

    companion object {
        private val TalkFeedbackLearnedDiffCallback =
            ItemDiffCallback<Learned>(onItemsTheSame = { old, new -> old.type == new.type },
                onContentsTheSame = { old, new -> old == new })

        const val VIEW_TYPE_LABEL = 0
        const val VIEW_TYPE_EXPRESSION = 1
        const val VIEW_TYPE_GRAMMAR = 2
        const val VIEW_TYPE_PRONUNCIATION = 3
        const val VIEW_TYPE_AFTER_ANSWER = 4
    }
}