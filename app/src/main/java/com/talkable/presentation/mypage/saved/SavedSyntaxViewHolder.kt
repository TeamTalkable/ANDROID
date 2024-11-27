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
import com.talkable.presentation.review.model.Saved

class SavedSyntaxViewHolder(private val binding: ItemSavedSyntaxBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        initItemViewClickListener()
        initListenBtnClickListener()
    }

    fun onBind(item: Saved.Sentence) {
        val context = binding.root.context
        val backgroundColor = backgroundColors[item.status] ?: R.drawable.shape_main_fill_12_rect
        val textColor = textColors[item.status] ?: R.color.white

        binding.run {
            tvSavedSyntax.text = item.sentenceEnglish
            btnSavedSyntaxTag.text = item.status.getStatusText(context)
            tvSavedTranslation.text = item.sentenceKorean
            btnSavedSyntaxTag.background =
                ContextCompat.getDrawable(btnSavedSyntaxTag.context, backgroundColor)
            btnSavedSyntaxTag.setTextColor(
                ContextCompat.getColor(
                    btnSavedSyntaxTag.context,
                    textColor
                )
            )

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

    private fun initListenBtnClickListener() {
        with(binding) {
            btnSavedSyntaxListen.setOnClickListener {
                btnSavedSyntaxListen.isSelected = !btnSavedSyntaxListen.isSelected
            }
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