package com.talkable.presentation.mypage.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackExpressionBinding

class MyExpressionAdapter(private val feedbackList: List<Expression>) :
    RecyclerView.Adapter<MyFeedbackExpressionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyFeedbackExpressionViewHolder {
        val binding =
            ItemMyFeedbackExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyFeedbackExpressionViewHolder(binding)
    }

    override fun getItemCount() = feedbackList.size

    override fun onBindViewHolder(holder: MyFeedbackExpressionViewHolder, position: Int) {
        holder.bind(feedbackList[position])
    }
}