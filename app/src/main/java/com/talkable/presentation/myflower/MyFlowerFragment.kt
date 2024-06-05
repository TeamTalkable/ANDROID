package com.talkable.presentation.myflower

import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyFlowerBinding

class MyFlowerFragment : BindingFragment<FragmentMyFlowerBinding>(R.layout.fragment_my_flower) {

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initMyFlowerAdapter()
    }

    private fun initMyFlowerAdapter() = with(binding) {
        vpMyFlower.adapter = MyFlowerAdapter(this@MyFlowerFragment)
        //TabLayoutMediator(vpMyFlowerIndicator, vpMyFlower) { tab, position -> }.attach()
    }
}