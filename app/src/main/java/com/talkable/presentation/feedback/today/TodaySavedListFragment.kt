package com.talkable.presentation.feedback.today

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentTodaySavedListBinding
import com.talkable.presentation.feedback.today.model.TodaySaved
import com.talkable.presentation.feedback.today.model.TodaySavedModel
import com.talkable.presentation.mypage.saved.Constants


class TodaySavedListFragment :
    BindingFragment<FragmentTodaySavedListBinding>(R.layout.fragment_today_saved_list) {

    private lateinit var todaySavedAdapter: TodaySavedAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initSavedWordAdapter()
    }


    private fun initSavedWordAdapter() {
        todaySavedAdapter = TodaySavedAdapter()
        with(binding.rvSavedWord) {
            adapter = todaySavedAdapter
            layoutManager = LinearLayoutManager(context)
        }
        todaySavedAdapter.submitList(mockData.todaySavedList)
    }

    companion object {
        fun newInstance(category: TodaySavedCategory): TodaySavedListFragment {
            return TodaySavedListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.KEY_CATEGORY, category)
                }
            }
        }
    }

    private val mockData = TodaySavedModel(
        todaySavedId = 1,
        todaySavedList = listOf(
            TodaySaved(
                word = "inspire",
                translation = "",
                verb = "자극하다, 격려하다",
                noun = "창의성"
            ),
            TodaySaved(
                word = "resilience",
                translation = "",
                verb = "극복하다, 회복하다",
                noun = ""
            ),
            TodaySaved(
                word = "Met a great friend at school",
                translation = "학교에서 좋은 친구를 만났어.",
                verb = "",
                noun = ""
            )
        )
    )
}