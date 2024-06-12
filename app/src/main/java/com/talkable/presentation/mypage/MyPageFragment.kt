package com.talkable.presentation.mypage

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.CHART_KEY
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyPageBinding
import com.talkable.presentation.mypage.model.BarChart
import com.talkable.presentation.mypage.model.Chart
import com.talkable.presentation.mypage.model.MyPageModel

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.model = mockData
        initMyFlowerBtnClickListener()
        initChartDetailBtnClickListener()
        setClickEventOnTabLayout()
    }

    private fun initMyFlowerBtnClickListener() {
        binding.tvMyPageMyFlower.setOnClickListener {
            navigateToMyFlowerFragment()
        }
    }

    private fun navigateToMyFlowerFragment() {
        findNavController().navigate(R.id.action_my_page_to_my_flower)
    }

    private fun initChartDetailBtnClickListener() {
        binding.ivMyPageNavigateChartDetail.setOnClickListener {
            navigateToChartDetailFragment()
        }
    }

    private fun navigateToChartDetailFragment() {
        findNavController().navigate(
            R.id.action_my_page_to_my_page_chart_detail,
            bundleOf(CHART_KEY to mockData.chartData)
        )
    }

    private fun setClickEventOnTabLayout() {
        binding.tlMyPage.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    1 -> initMyPageBarChartAdapter(mockWeekBarData)
                    else -> initMyPageBarChartAdapter(mockMonthBarData)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initMyPageBarChartAdapter(data: List<BarChart>) {
        binding.rvMyPageTab.adapter = MyPageBarChartAdapter(
            requireContext(), data.maxOf { it.studyTime }
        ).apply {
            submitList(data)
        }
        setRecyclerviewItemDecoration()
    }

    private fun setRecyclerviewItemDecoration() {
        if (binding.rvMyPageTab.itemDecorationCount == 0) {
            binding.rvMyPageTab.addItemDecoration(
                MyPageBarChartDecorator(requireContext()),
            )
        }
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

        val mockWeekBarData = listOf(
            BarChart(
                id = 1,
                date = "3월 1주차",
                studyTime = 60
            ),
            BarChart(
                id = 2,
                date = "3월 2주차",
                studyTime = 30
            ),
            BarChart(
                id = 3,
                date = "3월 3주차",
                studyTime = 17
            ),
            BarChart(
                id = 4,
                date = "3월 4주차",
                studyTime = 50
            ),
        )

        val mockMonthBarData = listOf(
            BarChart(
                id = 9,
                date = "1월",
                studyTime = 30
            ),
            BarChart(
                id = 10,
                date = "2월",
                studyTime = 20
            ),
            BarChart(
                id = 5,
                date = "3월",
                studyTime = 110
            ),
            BarChart(
                id = 6,
                date = "4월",
                studyTime = 70
            ),
            BarChart(
                id = 7,
                date = "5월",
                studyTime = 80
            ),
            BarChart(
                id = 8,
                date = "6월",
                studyTime = 16
            ),
        )
    }
}