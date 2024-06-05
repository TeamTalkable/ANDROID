package com.talkable.presentation.myflower

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentMyFlowerDetailBinding
import com.talkable.presentation.myflower.model.MyFlowerGetDate
import com.talkable.presentation.myflower.model.MyFlowerItem
import com.talkable.presentation.myflower.model.MyFlowerItemType
import com.talkable.presentation.myflower.model.MyFlowerModel
import timber.log.Timber

class MyFlowerDetailFragment :
    BindingFragment<FragmentMyFlowerDetailBinding>(R.layout.fragment_my_flower_detail) {
    override fun initView() {
        binding.model = mockData
        binding.layoutMyFlowerProgress.model = mockData
        initMyFlowerItemAdapter()
    }

    private fun initMyFlowerItemAdapter() {
        binding.rvMyFlowerItem.adapter = MyFlowerDetailItemAdapter(requireContext()).apply {
            submitList(mockData.itemData)
        }
    }

    companion object {
        val mockData = MyFlowerModel(
            flowerId = 1,
            flowerImage = "https://github.com/TeamTalkable/ANDROID/assets/98076050/2aea7e4e-cea5-44bd-882d-5ffe749de174",
            flowerName = "코스모스",
            flowerStartDate = "2024.05.05",
            flowerEndDate = null,
            studyTime = "60",
            getDateData = listOf(
                MyFlowerGetDate(time = 0, "05.09"),
                MyFlowerGetDate(time = 20, "05.14"),
                MyFlowerGetDate(time = 40, "05.20"),
                MyFlowerGetDate(time = 70),
                MyFlowerGetDate(time = 100),
            ),
            itemData = listOf(
                MyFlowerItem(itemType = MyFlowerItemType.SUN, count = 1),
                MyFlowerItem(itemType = MyFlowerItemType.COMPOST),
                MyFlowerItem(itemType = MyFlowerItemType.WATER, count = 4),
                MyFlowerItem(itemType = MyFlowerItemType.MEDICINE),
                MyFlowerItem(itemType = MyFlowerItemType.WIND),
                MyFlowerItem(itemType = MyFlowerItemType.SHOVEL),
            )
        )
    }
}