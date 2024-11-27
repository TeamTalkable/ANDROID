package com.talkable.presentation.mypage.saved

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentSavedSyntaxBinding
import com.talkable.presentation.home.TodaySavedUiState
import com.talkable.presentation.home.TodaySavedViewModel
import com.talkable.presentation.home.model.MemorizationStatus
import com.talkable.presentation.home.model.TalkSavedModel
import kotlinx.coroutines.launch
import timber.log.Timber

class SavedSyntaxFragment :
    BindingFragment<FragmentSavedSyntaxBinding>(R.layout.fragment_saved_syntax) {

    private val viewModel: TodaySavedViewModel by activityViewModels()
    private lateinit var savedSyntaxAdapter: SavedSyntaxAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        collect()
        initTranslationBtnClickListener()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is TodaySavedUiState.Success -> {
                        setSavedSyntaxCount(uiState.data)
                        initSavedSyntaxAdapter(uiState.data)
                        initSavedSyntaxChipClickListener(uiState.data)
                    }

                    is TodaySavedUiState.Error -> Timber.e(uiState.errorMessage)
                    else -> Unit
                }
            }
        }
    }

    private fun initTranslationBtnClickListener() {
        with(binding) {
            layoutSavedSort.btnSavedTranslate.setOnClickListener {
                layoutSavedSort.btnSavedTranslate.isSelected =
                    !layoutSavedSort.btnSavedTranslate.isSelected
                for (i in 0 until rvSavedSyntax.childCount) {
                    val holder =
                        rvSavedSyntax.findViewHolderForAdapterPosition(i) as? SavedSyntaxViewHolder
                    holder?.initSyntaxTranslation()
                }
            }
        }
    }

    private fun initSavedSyntaxAdapter(data: TalkSavedModel) {
        savedSyntaxAdapter = SavedSyntaxAdapter()
        with(binding.rvSavedSyntax) {
            adapter = savedSyntaxAdapter
        }
        savedSyntaxAdapter.submitList(data.savedSentenceList)
    }

    private fun setSavedSyntaxCount(data: TalkSavedModel) {
        binding.layoutSavedSort.tvSavedCount.text =
            getString(R.string.tv_saved_sentence_count, data.savedSentenceList.size)
    }

    private fun initSavedSyntaxChipClickListener(data: TalkSavedModel) {
        binding.layoutSavedSort.cgSavedList.setOnCheckedStateChangeListener { chipGroup, _ ->
            val filteredData = when (chipGroup.checkedChipId) {
                R.id.chip_saved_difficult -> {
                    data.savedSentenceList.filter { it.status == MemorizationStatus.DIFFICULT }
                }

                R.id.chip_saved_memorizing -> {
                    data.savedSentenceList.filter { it.status == MemorizationStatus.MEMORIZING }
                }

                R.id.chip_saved_memorized -> {
                    data.savedSentenceList.filter { it.status == MemorizationStatus.MEMORIZED }
                }

                else -> {
                    data.savedSentenceList
                }
            }
            savedSyntaxAdapter.submitList(filteredData)
        }
    }

    companion object {
        fun newInstance(category: SavedCategory): SavedSyntaxFragment {
            return SavedSyntaxFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.KEY_CATEGORY, category)
                }
            }
        }
    }
}