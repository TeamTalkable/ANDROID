package com.talkable.presentation.feedback.today

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTodayFeedbackExpressionBinding
import com.talkable.databinding.ItemTodayFeedbackGrammarBinding
import com.talkable.databinding.ItemTodayFeedbackPronunciationBinding
import com.talkable.presentation.feedback.today.model.TodayFeedback

class TodayFeedbackAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context) }
    private var feedbackList: List<TodayFeedback> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return when (feedbackList[position]) {
            is TodayFeedback.Expression -> VIEW_TYPE_EXPRESSION
            is TodayFeedback.Grammar -> VIEW_TYPE_GRAMMAR
            else -> VIEW_TYPE_PRONUNCIATION
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EXPRESSION -> {
                val binding = ItemTodayFeedbackExpressionBinding.inflate(
                    inflater, parent, false
                )
                FeedbackExpressionViewHolder(binding)
            }

            VIEW_TYPE_GRAMMAR -> {
                val binding = ItemTodayFeedbackGrammarBinding.inflate(
                    inflater, parent, false
                )
                FeedbackGrammarViewHolder(binding)
            }

            else -> {
                val binding = ItemTodayFeedbackPronunciationBinding.inflate(
                    inflater, parent, false
                )
                FeedbackPronunciationViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val isLastItem = position == itemCount - 1
        val isOnlyItem = itemCount == 1

        when (val data = feedbackList[position]) {
            is TodayFeedback.Expression -> (holder as? FeedbackExpressionViewHolder)?.run {
                onBind(data, isLastItem, isOnlyItem)
            }

            is TodayFeedback.Grammar -> (holder as? FeedbackGrammarViewHolder)?.run {
                onBind(data, isLastItem, isOnlyItem)
            }

            is TodayFeedback.Pronunciation -> (holder as? FeedbackPronunciationViewHolder)?.run {
                onBind(data, isLastItem, isOnlyItem)
            }
        }
    }

    override fun getItemCount(): Int = feedbackList.size

    fun submitList(newList: List<TodayFeedback>) {
        feedbackList = newList
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_EXPRESSION = 1
        const val VIEW_TYPE_GRAMMAR = 2
        const val VIEW_TYPE_PRONUNCIATION = 3
    }
}