package com.talkable.presentation.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemChallengeParticipationBinding

class ChallengeParticipationAdapter(private val participationList: List<Participation>) :
    RecyclerView.Adapter<ParticipationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipationViewHolder {
        val binding =
            ItemChallengeParticipationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ParticipationViewHolder(binding)
    }

    override fun getItemCount() = participationList.size

    override fun onBindViewHolder(holder: ParticipationViewHolder, position: Int) {
        holder.bind(participationList[position])
    }
}