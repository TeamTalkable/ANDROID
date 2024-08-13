package com.talkable.presentation.feedback.today

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemTodaySavedBinding
import com.talkable.presentation.feedback.today.model.TodaySaved

class TodaySavedViewHolder(private val binding: ItemTodaySavedBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodaySaved, isLastItem: Boolean) {
        binding.apply {
            tvTodaySavedWord.text = item.word

            tvTodaySavedVerb.apply {
                text = if (item.verb.isNullOrEmpty()) {
                    item.translation
                } else {
                    context.getString(R.string.tv_today_saved_verb, item.verb)
                }
                visibility = if (text.isNullOrEmpty()) GONE else VISIBLE
            }

            tvTodaySavedNoun.apply {
                text = context.getString(R.string.tv_today_saved_noun, item.noun)
                visibility = if (item.noun.isNullOrEmpty()) GONE else VISIBLE
            }
            dividerTodaySaved.visibility = if (isLastItem) GONE else VISIBLE
        }
    }

    companion object {
        fun from(parent: ViewGroup): TodaySavedViewHolder {
            val binding =
                ItemTodaySavedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return TodaySavedViewHolder(binding)
        }
    }
}