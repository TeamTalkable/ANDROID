package com.talkable.presentation.mypage.saved

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentSavedWordBinding
import com.talkable.presentation.mypage.saved.model.SavedListModel
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedWordFragment : BindingFragment<FragmentSavedWordBinding>(R.layout.fragment_saved_word) {

    private lateinit var savedWordAdapter: SavedWordAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initSavedWordAdapter()
        initSavedWordChipClickListener()
        initTranslationBtnClickListener()
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

    private fun initSavedWordAdapter() {
        savedWordAdapter = SavedWordAdapter()
        with(binding.rvSavedWord) {
            adapter = savedWordAdapter
            layoutManager = LinearLayoutManager(context)
        }
        // Set initial data
        savedWordAdapter.submitList(mockData.savedWordList)
    }

    private fun initSavedWordChipClickListener() {
        binding.layoutSavedSort.cgSavedList.setOnCheckedStateChangeListener { chipGroup, _ ->
            val newData = when (chipGroup.checkedChipId) {
                R.id.chip_saved_difficult -> difficultWord.savedWordList
                R.id.chip_saved_memorizing -> memorizingWord.savedWordList
                R.id.chip_saved_memorized -> memorizedWord.savedWordList
                else -> mockData.savedWordList
            }
            savedWordAdapter.submitList(newData)
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

    private val mockData = SavedListModel(
        savedWordId = 1,
        savedWordList = listOf(
            SavedWord(
                type = 0,
                word = "justice",
                translation = "1. 정의, 공정 \n2. 사법, 재판",
                tag = "어려워요"
            ),
            SavedWord(
                type = 1,
                word = "library",
                translation = "도서관",
                tag = "외웠어요"
            ),
            SavedWord(
                type = 2,
                word = "transparent",
                translation = "투명한",
                tag = "암기 중"
            )
        )
    )

    private val difficultWord = SavedListModel(
        savedWordId = 2,
        savedWordList = listOf(
            SavedWord(
                type = 0,
                word = "obfuscate",
                translation = "혼란스럽게 만들다",
                tag = "어려워요"
            ),
            SavedWord(
                type = 0,
                word = "pernicious",
                translation = "치명적인, 해로운",
                tag = "어려워요"
            ),
            SavedWord(
                type = 0,
                word = "esoteric",
                translation = "난해한, 소수만 이해하는",
                tag = "어려워요"
            )
        )
    )

    private val memorizingWord = SavedListModel(
        savedWordId = 3,
        savedWordList = listOf(
            SavedWord(
                type = 1,
                word = "understand",
                translation = "이해하다",
                tag = "암기 중"
            ),
            SavedWord(
                type = 1,
                word = "comprehend",
                translation = "이해하다, 파악하다",
                tag = "암기 중"
            ),
            SavedWord(
                type = 1,
                word = "grasp",
                translation = "1. 꽉 잡다 \n2. 이해하다",
                tag = "암기 중"
            )
        )
    )

    private val memorizedWord = SavedListModel(
        savedWordId = 4,
        savedWordList = listOf(
            SavedWord(
                type = 2,
                word = "honest",
                translation = "정직한",
                tag = "외웠어요"
            ),
            SavedWord(
                type = 2,
                word = "frank",
                translation = "솔직한",
                tag = "외웠어요"
            ),
            SavedWord(
                type = 2,
                word = "sincere",
                translation = "진실한",
                tag = "외웠어요"
            )
        )
    )
}