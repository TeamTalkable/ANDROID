package com.talkable.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyPageBinding
import com.talkable.presentation.mypage.model.Chart
import com.talkable.presentation.mypage.model.MyPageModel

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.model = mockData
        initMyFlowerBtnClickListener()
    }

    private fun initMyFlowerBtnClickListener() {
        binding.tvMyPageMyFlower.setOnClickListener {
            navigateToMyFlowerFragment()
        }
    }

    private fun navigateToMyFlowerFragment() {
        findNavController().navigate(R.id.action_my_page_to_my_flower)
    }

    companion object {
        val mockData = MyPageModel(
            1,
            "박소현",
            "https://github.com/TeamTalkable/ANDROID/assets/98076050/aa5e4dbd-c479-4bfe-b26d-921ddd6c9ed1",
            true,
            "10",
            "18",
            "200",
            "09:00 AM",
            "매일",
            Chart(10, 20, 30, 40, 20, 40, 60, 90, false)
        )
    }
}