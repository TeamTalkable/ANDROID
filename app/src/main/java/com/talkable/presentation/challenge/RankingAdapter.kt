package com.talkable.presentation.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemChallengeRankingBinding

class RankingAdapter : ListAdapter<Ranking, RecyclerView.ViewHolder>(RankingAdapterDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding =
            ItemChallengeRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RankingViewHolder).bind(currentList[position])
    }

    companion object {
        private val RankingAdapterDiffCallback =
            ItemDiffCallback<Ranking>(
                onItemsTheSame = { old, new -> old.name == new.name },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}