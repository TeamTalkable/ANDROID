package com.talkable.presentation.mypage

import androidx.annotation.Px
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.CHART_KEY
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentMyPageBinding
import com.talkable.presentation.mypage.model.BarChart
import com.talkable.presentation.mypage.model.CalendarModel
import com.talkable.presentation.mypage.model.Chart
import com.talkable.presentation.mypage.model.MyPageModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.model = mockData
        initNavigateFeedbackBtnClickListener()
        initMyFlowerBtnClickListener()
        initChartDetailBtnClickListener()
        setClickEventOnTabLayout()
        initMyPageCalendarAdapter(getMonthDays(mockData.calendarYear, mockData.calendarMonth))
    }

    private fun initNavigateFeedbackBtnClickListener(){
        binding.tvMyPageNavigateFeedback.setOnClickListener {
            navigateToFeedbackStore()
        }
    }

    private fun navigateToFeedbackStore() = findNavController().navigate(R.id.action_my_page_to_my_page_feedback)

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
            R.id.action_my_page_to_my_page_chart_detail, bundleOf(CHART_KEY to mockData.chartData)
        )
    }

    private fun setClickEventOnTabLayout() {
        binding.tlMyPage.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        setCalendarVisible(true)
                        setBarChartVisible(false)
                    }

                    1 -> {
                        setCalendarVisible(false)
                        setBarChartVisible(true)
                        initMyPageBarChartAdapter(mockWeekBarData)
                    }

                    else -> {
                        setCalendarVisible(false)
                        setBarChartVisible(true)
                        initMyPageBarChartAdapter(mockMonthBarData)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initMyPageBarChartAdapter(data: List<BarChart>) {
        binding.rvMyPageTab.adapter =
            MyPageBarChartAdapter(requireContext(), data.maxOf { it.studyTime }).apply {
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

    private fun initMyPageCalendarAdapter(item: List<CalendarModel>) {
        with(binding) {
            vpMyPageCalendar.apply {
                adapter = MyPageCalendarAdapter(requireContext()).apply { submitList(item) }
                offscreenPageLimit = 1
                post { setCurrentItem(findTodayIndex(item)) }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        calendar = item[position]
                    }

                    override fun onPageScrolled(
                        position: Int, positionOffset: Float, @Px positionOffsetPixels: Int
                    ) {
                    }
                })
            }
        }
        setDeviceOffset()
    }

    private fun findTodayIndex(data: List<CalendarModel>): Int = data.indexOfFirst {
        it.date == LocalDate.now().format(DateTimeFormatter.ofPattern("d")).toInt()
    }

    private fun setDeviceOffset() {
        val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
        val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
        val screenWidth = resources.displayMetrics.widthPixels
        val offset = screenWidth - pageWidth - pageMargin
        setPageTransformer(offset)
    }

    private fun setPageTransformer(offset: Float) = with(binding) {
        vpMyPageCalendar.setPageTransformer { page, position ->

            page.translationX = position * -offset
            val absPos = Math.abs(position)
            var alphaByPosition = 1 - absPos
            if (alphaByPosition < 0.5) {
                alphaByPosition = 0.5f
            }
            page.alpha = alphaByPosition
            setCalendarCenterDayVisible((position % 1) == 0.0f)
        }
    }

    private fun getMonthDays(year: Int, month: Int): List<CalendarModel> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val listOfDays = mutableListOf<CalendarModel>()

        val dayOfWeekFormat = SimpleDateFormat("E", Locale.KOREAN)

        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val weekDay = dayOfWeekFormat.format(calendar.time)
            listOfDays.add(CalendarModel(weekDay, day))
        }

        return listOfDays
    }

    private fun setBarChartVisible(isVisible: Boolean) = with(binding) {
        rvMyPageTab.isVisible = isVisible
        dividerMyPage3.isVisible = isVisible
    }

    private fun setCalendarVisible(isVisible: Boolean) = with(binding) {
        tvMyPageCalendarYearMonth.isVisible = isVisible
        vpMyPageCalendar.isVisible = isVisible
        setCalendarCenterDayVisible(isVisible)
    }

    private fun setCalendarCenterDayVisible(isVisible: Boolean) = with(binding) {
        ivMyPageCalendarGuide.isVisible = isVisible
        tvMyPageCalendarWeek.isVisible = isVisible
        tvMyPageCalendarDate.isVisible = isVisible
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
            Chart(10, 20, 30, 40, 20, 40, 60, 90, false),
            2024,
            4
        )

        val mockWeekBarData = listOf(
            BarChart(
                id = 1, date = "3월 1주차", studyTime = 60
            ),
            BarChart(
                id = 2, date = "3월 2주차", studyTime = 30
            ),
            BarChart(
                id = 3, date = "3월 3주차", studyTime = 17
            ),
            BarChart(
                id = 4, date = "3월 4주차", studyTime = 50
            ),
        )

        val mockMonthBarData = listOf(
            BarChart(
                id = 9, date = "1월", studyTime = 30
            ),
            BarChart(
                id = 10, date = "2월", studyTime = 20
            ),
            BarChart(
                id = 5, date = "3월", studyTime = 110
            ),
            BarChart(
                id = 6, date = "4월", studyTime = 70
            ),
            BarChart(
                id = 7, date = "5월", studyTime = 80
            ),
            BarChart(
                id = 8, date = "6월", studyTime = 16
            ),
        )
    }
}