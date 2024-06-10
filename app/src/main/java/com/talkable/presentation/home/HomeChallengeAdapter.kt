package com.talkable.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.databinding.ItemHomeChallengeBinding

class HomeChallengeAdapter(private val imgList: List<Int>) :
    RecyclerView.Adapter<HomeChallengeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemHomeChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUri: Int) {
            binding.ivChallenge.load(imgUri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }
}