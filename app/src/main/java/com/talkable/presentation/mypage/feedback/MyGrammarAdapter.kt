package com.talkable.presentation.mypage.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talkable.databinding.ItemMyFeedbackGrammarBinding

class MyGrammarAdapter(private val feedbackList: List<Grammar>) :
    RecyclerView.Adapter<MyFeedbackGrammarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFeedbackGrammarViewHolder {
        val binding =
            ItemMyFeedbackGrammarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyFeedbackGrammarViewHolder(binding)
    }

    override fun getItemCount() = feedbackList.size

    override fun onBindViewHolder(holder: MyFeedbackGrammarViewHolder, position: Int) {
        holder.bind(feedbackList[position])
    }
}