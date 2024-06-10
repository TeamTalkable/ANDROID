//package com.talkable.presentation.home
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.talkable.databinding.ItemHomeCalendarBinding
//
//class ChallengeAdapter(private val imageList: List<Any>, private val onBannerClickListener: OnBannerClickListener) : RecyclerView.Adapter<ChallengeAdapter.BannerViewHolder>() {
//
//    // Define listener interface to handle click events
//    interface OnBannerClickListener {
//        fun onBannerClick(position: Int)
//    }
//
//    inner class ChallengeViewHolder(private val binding: ItemHomeCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
//        init {
//            // Define method to be called when each banner is clicked
//            binding.root.setOnClickListener {
//                val position = adapterPosition // Get the position of the clicked banner
//                if (position != RecyclerView.NO_POSITION) {
//                    // Pass the position of the clicked banner to the click listener
//                    onBannerClickListener.onBannerClick(position)
//                }
//            }
//        }
//
//        fun bind(item: Any) {
//            // Use Glide to load and set the image
//            Glide.with(binding.imgBanner)
//                .load(item)
//                .into(binding.imgBanner)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
//        // Inflate the item layout and create a new ViewHolder
//        val binding = ItemHomeCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ChallengeViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
//        // Bind data to each item
//        val currentItem = imageList[position]
//        holder.bind(currentItem)
//    }
//
//    override fun getItemCount(): Int {
//        // Return the total number of items
//        return imageList.size
//    }
//}