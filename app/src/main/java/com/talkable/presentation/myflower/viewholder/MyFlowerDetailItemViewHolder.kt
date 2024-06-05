package com.talkable.presentation.myflower.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.talkable.R
import com.talkable.databinding.ItemMyFlowerItemBinding
import com.talkable.presentation.myflower.model.MyFlowerItem
import com.talkable.presentation.myflower.model.MyFlowerItemType

class MyFlowerDetailItemViewHolder(private val binding: ItemMyFlowerItemBinding) :
    ViewHolder(binding.root) {
    fun bind(data: MyFlowerItem) = with(binding) {
        model = data
        executePendingBindings()
        binding.ivMyFlowerItem.setImageResource(setItemIconImage(data))
    }

    private fun setItemIconImage(data: MyFlowerItem): Int {
        return when (data.itemType) {
            MyFlowerItemType.SUN -> R.drawable.ic_my_flower_item_sun
            MyFlowerItemType.COMPOST -> R.drawable.ic_my_flower_item_compost
            MyFlowerItemType.WATER -> R.drawable.ic_my_flower_item_water
            MyFlowerItemType.MEDICINE -> R.drawable.ic_my_flower_item_medicine
            MyFlowerItemType.WIND -> R.drawable.ic_my_flower_item_wind
            MyFlowerItemType.SHOVEL -> R.drawable.ic_my_flower_item_shovel
        }
    }
}