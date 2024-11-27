package com.talkable.presentation.mypage.saved

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentSavedWordBinding
import com.talkable.presentation.review.model.MemorizationStatus
import com.talkable.presentation.review.model.TalkSavedModel
import com.talkable.presentation.review.model.TodaySavedUiState
import com.talkable.presentation.review.model.TodaySavedViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class SavedWordFragment : BindingFragment<FragmentSavedWordBinding>(R.layout.fragment_saved_word) {

    private val viewModel: TodaySavedViewModel by activityViewModels()
    private lateinit var savedWordAdapter: SavedWordAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        collect()
        initSavedWordAdapter()
        initTranslationBtnClickListener()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is TodaySavedUiState.Success -> {
                        setSavedWordCount(uiState.data)
                        initSetSavedWordAdapter(uiState.data)
                        initSavedWordChipClickListener(uiState.data)
                    }

                    is TodaySavedUiState.Error -> Timber.e(uiState.errorMessage)
                    else -> Unit
                }
            }
        }
    }

    private fun initSavedWordAdapter() {
        savedWordAdapter = SavedWordAdapter()
        with(binding.rvSavedWord) {
            adapter = savedWordAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSetSavedWordAdapter(data: TalkSavedModel) {
        val savedItems = data.savedWordList.map { word ->
            word
        }
        savedWordAdapter.submitList(savedItems)
    }

    private fun setSavedWordCount(data: TalkSavedModel) {
        binding.layoutSavedSort.tvSavedCount.text =
            getString(R.string.tv_saved_word_count, data.savedWordList.size)
    }

    private fun initTranslationBtnClickListener() {
        with(binding) {
            layoutSavedSort.btnSavedTranslate.setOnClickListener {
                layoutSavedSort.btnSavedTranslate.isSelected =
                    !layoutSavedSort.btnSavedTranslate.isSelected
                for (i in 0 until rvSavedWord.childCount) {
                    val holder =
                        rvSavedWord.findViewHolderForAdapterPosition(i) as? SavedWordViewHolder
                    holder?.initWordTranslation()
                }
            }
        }
    }

    private fun initSavedWordChipClickListener(data: TalkSavedModel) {
        binding.layoutSavedSort.cgSavedList.setOnCheckedStateChangeListener { chipGroup, _ ->
            val filteredData = when (chipGroup.checkedChipId) {
                R.id.chip_saved_difficult -> {
                    data.savedWordList.filter { it.status == MemorizationStatus.DIFFICULT }
                }

                R.id.chip_saved_memorizing -> {
                    data.savedWordList.filter { it.status == MemorizationStatus.MEMORIZING }
                }

                R.id.chip_saved_memorized -> {
                    data.savedWordList.filter { it.status == MemorizationStatus.MEMORIZED }
                }

                else -> {
                    data.savedWordList
                }
            }

            savedWordAdapter.submitList(filteredData)
        }
    }

    companion object {
        fun newInstance(category: SavedCategory): SavedWordFragment {
            return SavedWordFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.KEY_CATEGORY, category)
                }
            }
        }
    }
}