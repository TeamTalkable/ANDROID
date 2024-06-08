package com.talkable.presentation.myflower

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentMyFlowerListBinding
import com.talkable.presentation.myflower.model.MyFlowerEnd

class MyFlowerListFragment :
    BindingFragment<FragmentMyFlowerListBinding>(R.layout.fragment_my_flower_list) {
    override fun initView() {
        initMyFlowerListAdapter()
    }

    private fun initMyFlowerListAdapter() {
        binding.rvMyFlowerList.adapter =
            MyFlowerListAdapter(requireContext(), onClickMyFlowerItem = {

            }).apply {
                submitList(mockData)
            }
    }

    companion object {
        private val mockData = listOf(
            MyFlowerEnd(
                flowerId = 1,
                flowerImage = "https://github.com/TeamTalkable/ANDROID/assets/98076050/7b8ee34b-7d83-4ec2-9895-4a1b685fd9f1"
            ),
            MyFlowerEnd(
                flowerId = 2,
                flowerImage = "https://github.com/TeamTalkable/ANDROID/assets/98076050/45b692ac-2fb1-45d1-b3b1-3d887c08928a"
            )
        )
    }
}