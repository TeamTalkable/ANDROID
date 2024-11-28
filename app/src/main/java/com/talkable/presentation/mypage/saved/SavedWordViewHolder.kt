package com.talkable.presentation.mypage.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talkable.R
import com.talkable.databinding.ItemSavedWordBinding
import com.talkable.presentation.review.model.MemorizationStatus
import com.talkable.presentation.review.model.Saved

class SavedWordViewHolder(private val binding: ItemSavedWordBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        initItemViewClickListener()
        initListenBtnClickListener()
    }

    fun onBind(item: Saved.Word) {
        val context = binding.root.context
        val backgroundColor = backgroundColors[item.status] ?: R.drawable.shape_main_fill_12_rect
        val textColor = textColors[item.status] ?: R.color.white

        binding.run {
            tvSavedWord.text = item.wordEnglish
            btnSavedWordTag.text = item.status.getStatusText(context)
            tvSavedTranslation.text = item.wordKorean
            btnSavedWordTag.background =
                ContextCompat.getDrawable(context, backgroundColor)
            btnSavedWordTag.setTextColor(ContextCompat.getColor(context, textColor))

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
        }
    }

    private fun initListenBtnClickListener() {
        with(binding) {
            btnSavedWordListen.setOnClickListener {
                btnSavedWordListen.isSelected = !btnSavedWordListen.isSelected
            }
        }
    }

    companion object {
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
            MemorizationStatus.DIFFICULT to R.drawable.shape_main_fill_12_rect,
            MemorizationStatus.MEMORIZING to R.drawable.shape_main1_fill_12_rect,
            MemorizationStatus.MEMORIZED to R.drawable.shape_gray_fill_12_rect
        )

        val textColors = mapOf(
            MemorizationStatus.DIFFICULT to R.color.white,
            MemorizationStatus.MEMORIZING to R.color.font,
            MemorizationStatus.MEMORIZED to R.color.white
        )
    }
}