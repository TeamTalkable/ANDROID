package com.talkable.presentation.talk

import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemTalkAiBinding
import com.talkable.databinding.ItemTalkUserBinding

class AiTalkViewHolder(private val binding: ItemTalkAiBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(talkData: TalkData) {
        binding.run {
            tvTalkAi.text = talkData.message
        }
    }
}

class UserTalkViewHolder(private val binding: ItemTalkUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(talkData: TalkData) {
        binding.run {
            tvTalkUser.text = talkData.message
        }
    }
}