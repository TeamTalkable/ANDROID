package com.talkable.presentation.mypage.saved

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemSavedSyntaxBinding
import com.talkable.presentation.mypage.saved.SavedWordViewHolder.Companion.backgroundColors
import com.talkable.presentation.mypage.saved.SavedWordViewHolder.Companion.textColors
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxViewHolder(private val binding: ItemSavedSyntaxBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        initItemViewClickListener()
    }

    fun onBind(item: SavedWord) {
        val backgroundColor = backgroundColors[item.type] ?: R.drawable.shape_main_fill_12_rect
        val textColor = textColors[item.type] ?: R.color.white

        binding.run {
            tvSavedWord.text = item.word
            btnSavedWordTag.text = item.tag
            tvSavedTranslation.text = item.translation
            btnSavedWordTag.background =
                ContextCompat.getDrawable(btnSavedWordTag.context, backgroundColor)
            btnSavedWordTag.setTextColor(ContextCompat.getColor(btnSavedWordTag.context, textColor))

            tvSavedTranslation.visibility = View.GONE
        }
    }

    private fun initItemViewClickListener() {
        itemView.setOnClickListener {
            initSyntaxTranslation()
        }
    }

    fun initSyntaxTranslation() {
        itemView.isSelected = !itemView.isSelected
        with(binding) {
            tvSavedTranslation.visibility = if (itemView.isSelected) View.VISIBLE else View.GONE
            btnSavedWordTag.visibility = if (itemView.isSelected) View.GONE else View.VISIBLE
        }
    }
}