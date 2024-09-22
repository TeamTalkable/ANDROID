package com.talkable.presentation.challenge

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ChallengeRecruitingAdapter :
    ListAdapter<Recruiting, RecruitingViewHolder>(ChallengeRecruitingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitingViewHolder {
        return RecruitingViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecruitingViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class ChallengeRecruitingDiffCallback : DiffUtil.ItemCallback<Recruiting>() {
    override fun areItemsTheSame(oldItem: Recruiting, newItem: Recruiting): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Recruiting, newItem: Recruiting): Boolean {
        return oldItem == newItem
    }
}