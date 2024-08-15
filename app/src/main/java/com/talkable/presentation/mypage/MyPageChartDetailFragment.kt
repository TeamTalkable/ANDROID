package com.talkable.presentation.mypage

import android.os.Parcelable
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.CHART_KEY
import com.talkable.core.util.intent.parcelable
import com.talkable.databinding.FragmentMyPageChartDetailBinding
import com.talkable.presentation.mypage.model.Chart
import com.talkable.presentation.mypage.model.ChartDetail

class MyPageChartDetailFragment :
    BindingFragment<FragmentMyPageChartDetailBinding>(R.layout.fragment_my_page_chart_detail) {
    override fun initView() {
        binding.chartDetail = mockData
        binding.chart = getChartData()
        setAppbarStatus()
        initBackBtnClickListener()
        initFeedbackDetailChipClickListener()
    }

    private fun setAppbarStatus() {
        binding.layoutMyPageChartDetailAppBar.leftBackIsVisible = true
        binding.layoutMyPageChartDetailAppBar.rightBackIsVisible = false
        binding.layoutMyPageChartDetailAppBar.ivAppBarBack.setImageResource(R.drawable.ic_my_page_cancel)
    }

    private fun getChartData() = requireArguments().parcelable<Parcelable>(CHART_KEY) as? Chart

    private fun initBackBtnClickListener() {
        binding.layoutMyPageChartDetailAppBar.ivAppBarBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun initFeedbackDetailChipClickListener() {
        binding.cvMyPageChart.updateChartSelected(com.talkable.core.view.Chart.GRAMMAR)
        binding.cgMyPageChartDetail.setOnCheckedStateChangeListener { chipGroup, ints ->
            with(binding.tvMyPageChartDetailDescription) {
                when (chipGroup.checkedChipId) {
                    R.id.chip_my_page_chart_detail_grammar -> {
                        binding.cvMyPageChart.updateChartSelected(com.talkable.core.view.Chart.GRAMMAR)
                        text = mockData.grammarDetail
                    }

                    R.id.chip_my_page_chart_detail_expression -> {
                        binding.cvMyPageChart.updateChartSelected(com.talkable.core.view.Chart.EXPRESSION)
                        text = mockData.expressionDetail
                    }

                    R.id.chip_my_page_chart_detail_listening -> {
                        binding.cvMyPageChart.updateChartSelected(com.talkable.core.view.Chart.LISTENING)
                        text = mockData.listeningDetail
                    }

                    R.id.chip_my_page_chart_detail_pronunciation -> {
                        binding.cvMyPageChart.updateChartSelected(com.talkable.core.view.Chart.PRONUNCIATION)
                        text = mockData.pronunciationDetail
                    }
                }
            }
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

    companion object {
        val mockData = ChartDetail(
            totalPercent = "70%",
            grammarDetail = "문장의 주어가 2인칭일 때에는 복수형을 사용한다는 점을 주의하세요!",
            listeningDetail = "문장이 길어질 때에도 끝까지 집중해보세요",
            expressionDetail = "노트북은 콩글리시에요! 학교에서 있었던 일을 이야기할 때, \"랩탑\"이라는 표현으로 대체해보세요",
            pronunciationDetail = "th 발음에 주의하며 말해보세요",
        )
    }
}