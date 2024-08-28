package com.talkable.presentation.mypage.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            tvSavedSyntax.text = item.word
            btnSavedSyntaxTag.text = item.tag
            tvSavedTranslation.text = item.translation
            btnSavedSyntaxTag.background =
                ContextCompat.getDrawable(btnSavedSyntaxTag.context, backgroundColor)
            btnSavedSyntaxTag.setTextColor(ContextCompat.getColor(btnSavedSyntaxTag.context, textColor))

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
        }
    }

    companion object {
        fun from(parent: ViewGroup): SavedSyntaxViewHolder {
            val binding =
                ItemSavedSyntaxBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return SavedSyntaxViewHolder(binding)
        }
    }
}