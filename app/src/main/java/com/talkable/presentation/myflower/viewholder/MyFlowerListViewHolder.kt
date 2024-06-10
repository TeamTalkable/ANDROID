package com.talkable.presentation.myflower.viewholder

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemMyFlowerListBinding
import com.talkable.presentation.myflower.model.MyFlowerEnd

class MyFlowerListViewHolder(
    private val binding: ItemMyFlowerListBinding,
    private val onClickMyFlowerItem: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: MyFlowerEnd) = with(binding) {
        model = data
        executePendingBindings()
        binding.root.setOnClickListener {
            onClickMyFlowerItem(data.flowerId)
        }
    }

    fun bindEmpty() = with(binding) {
        ivMyFlowerList.setImageResource(R.drawable.ic_my_flower_list_empty)
        viewItemMyFlowerListBg.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
    }
}