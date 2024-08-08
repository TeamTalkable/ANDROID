package com.talkable.presentation.mypage.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedWordAdapter(
    private val savedWordList: List<SavedWord>
) : RecyclerView.Adapter<SavedWordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedWordViewHolder {
        return SavedWordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedWordViewHolder, position: Int) {
        holder.onBind(savedWordList[position])
    }

    override fun getItemCount() = savedWordList.size
}