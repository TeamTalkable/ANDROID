package com.talkable.presentation.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.databinding.ItemChallengeRecruitingBinding

class RecruitingViewHolder(
    private val binding: ItemChallengeRecruitingBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Recruiting) {
        binding.run {
            ivChallengeRecruiting.load(data.image)
            tvChallengeRecruitingTitle.text = data.title
            tvChallengeRecruitingDate.text = data.date
            tvChallengeRecruitingCount.text = data.count
        }
    }

    companion object {
        fun from(parent: ViewGroup): RecruitingViewHolder {
            val binding =
                ItemChallengeRecruitingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return RecruitingViewHolder(binding)
        }
    }
}