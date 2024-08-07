package com.talkable.presentation.mypage.saved

import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentSavedWordBinding
import com.talkable.presentation.mypage.saved.SavedFragment.Companion.NO_CHIP_SELECTED
import com.talkable.presentation.mypage.saved.model.SavedListModel
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedWordFragment :
    BindingFragment<FragmentSavedWordBinding>(R.layout.fragment_saved_word) {
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
        with(binding.rvSavedWord) {
            adapter = SavedWordAdapter(mockData.savedWordList)
        }
    }

    private fun initSavedWordChipClickListener() {
        binding.layoutSavedSort.cgSavedList.setOnCheckedStateChangeListener { chipGroup, ints ->
            when (chipGroup.checkedChipId) {
                NO_CHIP_SELECTED -> {
                    initSavedWordAdapter()
                }

                R.id.chip_saved_difficult -> {
                    with(binding.rvSavedWord) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SavedWordAdapter(difficultWord.savedWordList)
                    }
                }

                R.id.chip_saved_memorizing -> {
                    with(binding.rvSavedWord) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SavedWordAdapter(memorizingWord.savedWordList)
                    }
                }

                R.id.chip_saved_memorized -> {
                    with(binding.rvSavedWord) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SavedWordAdapter(memorizedWord.savedWordList)
                    }
                }
            }
        }
    }

    val mockData = SavedListModel(
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

    val difficultWord = SavedListModel(
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

    val memorizingWord = SavedListModel(
        savedWordId = 3,
        savedWordList = listOf(
            SavedWord(
                type = 1,
                word = "understand",
                translation = "이해하다",
                tag = "외웠어요"
            ),
            SavedWord(
                type = 1,
                word = "comprehend",
                translation = "이해하다, 파악하다",
                tag = "외웠어요"
            ),
            SavedWord(
                type = 1,
                word = "grasp",
                translation = "1. 꽉 잡다 \n2. 이해하다",
                tag = "외웠어요"
            )
        )
    )

    val memorizedWord = SavedListModel(
        savedWordId = 4,
        savedWordList = listOf(
            SavedWord(
                type = 2,
                word = "honest",
                translation = "정직한",
                tag = "암기 중"
            ),
            SavedWord(
                type = 2,
                word = "frank",
                translation = "솔직한",
                tag = "암기 중"
            ),
            SavedWord(
                type = 2,
                word = "sincere",
                translation = "진실한",
                tag = "암기 중"
            )
        )
    )
}