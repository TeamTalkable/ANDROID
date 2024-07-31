package com.talkable.presentation.mypage.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemSavedSyntaxBinding
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxAdapter(
    private val savedSyntaxList: List<SavedWord>
) : RecyclerView.Adapter<SavedSyntaxViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSyntaxViewHolder {
        val binding =
            ItemSavedSyntaxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedSyntaxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSyntaxViewHolder, position: Int) {
        holder.onBind(savedSyntaxList[position])
    }

    override fun getItemCount() = savedSyntaxList.size
}