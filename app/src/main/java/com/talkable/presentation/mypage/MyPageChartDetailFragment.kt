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

    private fun navigateToBack() = findNavController().popBackStack()

    companion object {
        val mockData = ChartDetail(
            totalPercent = "70%",
            grammarDetail = "과거의 사실을 얘기할 때는 동사의 과거형을 사용",
            listeningDetail = "과거의 사실을 얘기할 때는 동사의 과거형을 사용",
            expressionDetail = "과거의 사실을 얘기할 때는 동사의 과거형을 사용",
            pronunciationDetail = "과거의 사실을 얘기할 때는 동사의 과거형을 사용",
        )
    }
}