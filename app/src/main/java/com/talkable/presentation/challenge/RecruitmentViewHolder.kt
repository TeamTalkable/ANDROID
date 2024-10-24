package com.talkable.presentation.challenge

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.databinding.ItemChallengeRecruitmentBinding

class RecruitmentViewHolder(
    private val binding: ItemChallengeRecruitmentBinding,
    private val listener: ChallengeDialog.OnJoinCompleteListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Recruitment) {
        with(binding) {
            // 이미지 로딩
            ivChallengeRecruitmentItem.load(data.image)
            tvChallengeRecruitmentDate.text = data.date
            tvChallengeRecruitmentTitle.text = data.title

            // 클릭 시 다이얼로그 호출 (전체 data 전달)
            root.setOnClickListener {
                val context = it.context
                val dialog = ChallengeDialog(context, data, listener)
                dialog.show()
            }
        }
    }
}