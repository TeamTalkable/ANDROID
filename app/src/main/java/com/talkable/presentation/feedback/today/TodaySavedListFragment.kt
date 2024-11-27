package com.talkable.presentation.feedback.today

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentTodaySavedListBinding
import com.talkable.presentation.feedback.today.model.ItemType
import com.talkable.presentation.feedback.today.model.TodaySaved
import com.talkable.presentation.mypage.saved.Constants
import com.talkable.presentation.review.model.TalkSavedModel
import com.talkable.presentation.review.model.TodaySavedUiState
import com.talkable.presentation.review.model.TodaySavedViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class TodaySavedListFragment :
    BindingFragment<FragmentTodaySavedListBinding>(R.layout.fragment_today_saved_list) {

    private lateinit var todaySavedAdapter: TodaySavedAdapter
    private val viewModel: TodaySavedViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        collect()
        initSavedWordAdapter()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is TodaySavedUiState.Success -> initTodaySavedAdapter(uiState.data)
                    is TodaySavedUiState.Error -> Timber.e(uiState.errorMessage)
                    else -> Unit
                }
            }
        }
    }

    private fun initTodaySavedAdapter(data: TalkSavedModel) {
        val savedItems = data.savedWordList.map { word ->
            TodaySaved(word = word.wordEnglish, translation = word.wordKorean, type = ItemType.WORD)
        } + data.savedSentenceList.map { sentence ->
            TodaySaved(
                sentence = sentence.sentenceEnglish,
                translation = sentence.sentenceKorean,
                type = ItemType.SENTENCE
            )
        }

        todaySavedAdapter.submitList(savedItems)
    }

    private fun initSavedWordAdapter() {
        if (!::todaySavedAdapter.isInitialized) {
            todaySavedAdapter = TodaySavedAdapter()
        }
        binding.rvSavedWord.apply {
            adapter = todaySavedAdapter
            layoutManager = LinearLayoutManager(context)
        }
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
}