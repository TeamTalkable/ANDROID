package com.talkable.presentation.mypage.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemSavedWordBinding
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedWordViewHolder(private val binding: ItemSavedWordBinding) :
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
            initWordTranslation()
        }
    }

    fun initWordTranslation() {
        itemView.isSelected = !itemView.isSelected
        with(binding) {
            tvSavedTranslation.visibility = if (itemView.isSelected) View.VISIBLE else View.GONE
            btnSavedWordTag.visibility = if (itemView.isSelected) View.GONE else View.VISIBLE
        }
    }

    companion object {
        private const val TYPE_DIFFICULT = 0
        private const val TYPE_MEMORIZED = 1
        private const val TYPE_MEMORIZING = 2

        fun from(parent: ViewGroup): SavedWordViewHolder {
            val binding =
                ItemSavedWordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return SavedWordViewHolder(binding)
        }

        val backgroundColors = mapOf(
            TYPE_DIFFICULT to R.drawable.shape_main_fill_12_rect,
            TYPE_MEMORIZED to R.drawable.shape_main1_fill_12_rect,
            TYPE_MEMORIZING to R.drawable.shape_gray_fill_12_rect
        )

        val textColors = mapOf(
            TYPE_DIFFICULT to R.color.white,
            TYPE_MEMORIZED to R.color.font,
            TYPE_MEMORIZING to R.color.white
        )
    }
}