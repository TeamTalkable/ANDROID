package com.talkable.presentation.myflower

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.talkable.core.view.ItemDiffCallback
import com.talkable.databinding.ItemMyFlowerListBinding
import com.talkable.presentation.myflower.model.MyFlowerEnd
import com.talkable.presentation.myflower.viewholder.MyFlowerListViewHolder

class MyFlowerListAdapter(context: Context, private val onClickMyFlowerItem: (Int) -> Unit) :
    ListAdapter<MyFlowerEnd, MyFlowerListViewHolder>(
        MyFlowerListDiffCallback
    ) {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun getItemCount(): Int = 12

    override fun getItemViewType(position: Int): Int =
        if (currentList.getOrNull(position) != null) ITEM else EMPTY

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyFlowerListViewHolder {
        val binding =
            ItemMyFlowerListBinding.inflate(inflater, parent, false)
        return MyFlowerListViewHolder(binding, onClickMyFlowerItem)
    }

    override fun onBindViewHolder(holder: MyFlowerListViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> holder.bind(currentList[position])
            else -> holder.bindEmpty()
        }
    }

    companion object {
        private val MyFlowerListDiffCallback =
            ItemDiffCallback<MyFlowerEnd>(onItemsTheSame = { old, new -> old.flowerId == new.flowerId },
                onContentsTheSame = { old, new -> old == new })

        const val ITEM = 0
        const val EMPTY = 1
    }
}