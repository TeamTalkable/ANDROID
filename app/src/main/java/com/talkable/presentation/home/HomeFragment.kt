package com.talkable.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentHomeBinding

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        initViewPagerAdapter()
    }
    private val imageList = listOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        "https://example.com/image3.jpg"
    )

    private fun initViewPagerAdapter() {
        binding.viewpagerHomeChallenge.adapter = ImagePagerAdapter(imageList)
    }

    class ImagePagerAdapter(private val imageList: List<String>) :
        RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_home, parent, false)
            return ImageViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
            imageView.load(imageList[position])
        }

        override fun getItemCount(): Int = imageList.size
    }
}