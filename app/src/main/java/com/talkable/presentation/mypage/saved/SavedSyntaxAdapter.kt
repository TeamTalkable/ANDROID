package com.talkable.presentation.mypage.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxAdapter(
    private val savedSyntaxList: List<SavedWord>
) : RecyclerView.Adapter<SavedSyntaxViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSyntaxViewHolder {
        return SavedSyntaxViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedSyntaxViewHolder, position: Int) {
        holder.onBind(savedSyntaxList[position])
    }

    override fun getItemCount() = savedSyntaxList.size
}