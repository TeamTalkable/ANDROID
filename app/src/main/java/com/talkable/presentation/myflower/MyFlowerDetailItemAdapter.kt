package com.talkable.presentation.myflower

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemMyFlowerItemBinding
import com.talkable.presentation.myflower.model.MyFlowerItem
import com.talkable.presentation.myflower.viewholder.MyFlowerDetailItemViewHolder

class MyFlowerDetailItemAdapter(context: Context) :
    ListAdapter<MyFlowerItem, MyFlowerDetailItemViewHolder>(
        MyFlowerItemDiffCallback
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyFlowerDetailItemViewHolder {
        val binding =
            ItemMyFlowerItemBinding.inflate(inflater, parent, false)
        return MyFlowerDetailItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFlowerDetailItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val MyFlowerItemDiffCallback =
            ItemDiffCallback<MyFlowerItem>(onItemsTheSame = { old, new -> old.itemType == new.itemType },
                onContentsTheSame = { old, new -> old == new })
    }
}