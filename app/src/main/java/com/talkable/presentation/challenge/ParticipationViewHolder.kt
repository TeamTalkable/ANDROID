package com.talkable.presentation.challenge

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.databinding.ItemChallengeParticipationBinding

class ParticipationViewHolder(
    private val binding: ItemChallengeParticipationBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Participation) {
        with(binding) {
            ivChallengeParticipationItem.load(data.image)
            tvChallengeParticipationDate.text = data.date
            tvChallengeParticipationTitle.text = data.title
            progressbarChallengeParticipation.progress = data.progress
        }
    }
}