package com.talkable.presentation.talk

import androidx.recyclerview.widget.RecyclerView
import com.talkable.data.dto.request.Message
import com.talkable.databinding.ItemTalkAiBinding
import com.talkable.databinding.ItemTalkUserBinding

class AiTalkViewHolder(private val binding: ItemTalkAiBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(talkData: Message) {
        binding.run {
            tvTalkAi.text = talkData.content
        }
    }
}

class UserTalkViewHolder(private val binding: ItemTalkUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(talkData: Message) {
        binding.run {
            tvTalkUser.text = talkData.content
        }
    }
}