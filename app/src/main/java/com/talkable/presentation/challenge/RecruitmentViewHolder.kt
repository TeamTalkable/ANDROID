package com.talkable.presentation.challenge

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.databinding.ItemChallengeRecruitmentBinding

class RecruitmentViewHolder(
    private val binding: ItemChallengeRecruitmentBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Recruitment) {
        with(binding) {
            ivChallengeRecruitmentItem.load(data.image)
            tvChallengeRecruitmentDate.text = data.date
            tvChallengeRecruitmentTitle.text = data.title

            root.setOnClickListener {
                val context = it.context
                val dialog = ChallengeDialog(context, data.date, data.title)
                dialog.show()
            }
        }
    }
}