package com.talkable.presentation.myflower

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentMyFlowerDetailBinding
import com.talkable.presentation.myflower.MyFlowerListFragment.Companion.FLOWER_ID
import com.talkable.presentation.myflower.model.MyFlowerGetDate
import com.talkable.presentation.myflower.model.MyFlowerItem
import com.talkable.presentation.myflower.model.MyFlowerItemType
import com.talkable.presentation.myflower.model.MyFlowerModel
import timber.log.Timber

class MyFlowerDetailFragment :
    BindingFragment<FragmentMyFlowerDetailBinding>(R.layout.fragment_my_flower_detail) {
    override fun initView() {
        binding.model = getFlowerModelData()
        binding.layoutMyFlowerProgress.model = getFlowerModelData()
        initMyFlowerItemAdapter(getFlowerModelData())
        setSeekbarDisabled()
    }

    private fun getFlowerModelData(): MyFlowerModel? {
        return if (getFlowerId() != null) {
            mockData.find { it.flowerId == getFlowerId() }
        } else {
            mockData.getOrNull(0)
        }
    }

    private fun getFlowerId() = arguments?.getInt(FLOWER_ID)

    private fun initMyFlowerItemAdapter(data: MyFlowerModel?) {
        binding.rvMyFlowerItem.adapter = MyFlowerDetailItemAdapter(requireContext()).apply {
            submitList(data?.itemData)
        }
    }

    private fun setSeekbarDisabled() {
        binding.layoutMyFlowerProgress.sbMyFlower.isEnabled = false
    }

    companion object {
        val mockData = listOf(
            MyFlowerModel(
                flowerId = 3,
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
            ),
            MyFlowerModel(
                flowerId = 2,
                flowerImage = "https://github.com/TeamDon-tBe/ANDROID/assets/98076050/277a2c24-1523-4704-93fe-f8aca5bf34f2",
                flowerName = "장미꽃",
                flowerStartDate = "2024.04.09",
                flowerEndDate = "04.20",
                studyTime = "100",
                getDateData = listOf(
                    MyFlowerGetDate(time = 0, "04.09"),
                    MyFlowerGetDate(time = 20, "04.15"),
                    MyFlowerGetDate(time = 40, "04.16"),
                    MyFlowerGetDate(time = 70, "04.23"),
                    MyFlowerGetDate(time = 100, "04.30"),
                ),
                itemData = listOf(
                    MyFlowerItem(itemType = MyFlowerItemType.SUN, count = 1),
                    MyFlowerItem(itemType = MyFlowerItemType.COMPOST),
                    MyFlowerItem(itemType = MyFlowerItemType.WATER, count = 1),
                    MyFlowerItem(itemType = MyFlowerItemType.MEDICINE),
                    MyFlowerItem(itemType = MyFlowerItemType.WIND, count = 1),
                    MyFlowerItem(itemType = MyFlowerItemType.SHOVEL),
                )
            ), MyFlowerModel(
                flowerId = 1,
                flowerImage = "https://github.com/TeamDon-tBe/ANDROID/assets/98076050/6b6cecc7-4151-468f-926f-5de838dd17bd",
                flowerName = "보라꽃",
                flowerStartDate = "2024.04.09",
                flowerEndDate = "04.20",
                studyTime = "100",
                getDateData = listOf(
                    MyFlowerGetDate(time = 0, "04.09"),
                    MyFlowerGetDate(time = 20, "04.15"),
                    MyFlowerGetDate(time = 40, "04.16"),
                    MyFlowerGetDate(time = 70, "04.23"),
                    MyFlowerGetDate(time = 100, "04.30"),
                ),
                itemData = listOf(
                    MyFlowerItem(itemType = MyFlowerItemType.SUN, count = 1),
                    MyFlowerItem(itemType = MyFlowerItemType.COMPOST),
                    MyFlowerItem(itemType = MyFlowerItemType.WATER),
                    MyFlowerItem(itemType = MyFlowerItemType.MEDICINE),
                    MyFlowerItem(itemType = MyFlowerItemType.WIND),
                    MyFlowerItem(itemType = MyFlowerItemType.SHOVEL),
                )
            )
        )
    }
}