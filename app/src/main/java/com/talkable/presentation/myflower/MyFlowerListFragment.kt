package com.talkable.presentation.myflower

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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
                navigateToMyFlowerDetailFragment(it)
            }).apply {
                submitList(mockData)
            }
    }

    private fun navigateToMyFlowerDetailFragment(flowerId: Int) {
        findNavController().navigate(
            R.id.action_my_flower_to_my_flower_detail,
            bundleOf(FLOWER_ID to flowerId)
        )
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

        const val FLOWER_ID = "flowerId"
    }
}