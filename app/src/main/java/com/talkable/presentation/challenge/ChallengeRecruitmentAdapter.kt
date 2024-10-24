package com.talkable.presentation.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemChallengeRecruitmentBinding

class ChallengeRecruitmentAdapter(
    private val imgList: List<Recruitment>,
    private val onItemClicked: (Recruitment) -> Unit,
    private val listener: ChallengeDialog.OnJoinCompleteListener
) : RecyclerView.Adapter<RecruitmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
        val binding = ItemChallengeRecruitmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecruitmentViewHolder(binding, listener)
    }

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {
        val item = imgList[position]
        holder.bind(item)

        // 아이템 클릭 시 이벤트 처리
        holder.itemView.setOnClickListener {
            onItemClicked(item) // 클릭된 아이템 전달
        }
    }
}