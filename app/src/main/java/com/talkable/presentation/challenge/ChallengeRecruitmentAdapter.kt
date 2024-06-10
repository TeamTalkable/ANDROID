package com.talkable.presentation.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemChallengeRecruitmentBinding

class ChallengeRecruitmentAdapter(private val imgList: List<Recruitment>) :
    RecyclerView.Adapter<RecruitmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
        val binding =
            ItemChallengeRecruitmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RecruitmentViewHolder(binding)
    }

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
        holder.bind(imgList[position])
    }
}