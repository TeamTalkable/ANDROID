package com.talkable.presentation.challenge

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemChallengeRankingBinding

class RankingViewHolder(
    private val binding: ItemChallengeRankingBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Ranking) {
        with(binding) {
            tvRanking.text = data.rank
            tvRankingName.text = data.name
            tvRankingTime.text = data.time
        }
    }
}