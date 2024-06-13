package com.talkable.presentation.mypage.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackPronunciationBinding
import com.talkable.presentation.mypage.feedback.viewholder.MyFeedbackPronunciationViewHolder

class MyPronunciationAdapter(private val feedbackList: List<String>) :
    RecyclerView.Adapter<MyFeedbackPronunciationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyFeedbackPronunciationViewHolder {
        val binding =
            ItemMyFeedbackPronunciationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyFeedbackPronunciationViewHolder(binding)
    }

    override fun getItemCount() = feedbackList.size

    override fun onBindViewHolder(holder: MyFeedbackPronunciationViewHolder, position: Int) {
        holder.bind(feedbackList[position])
    }
}