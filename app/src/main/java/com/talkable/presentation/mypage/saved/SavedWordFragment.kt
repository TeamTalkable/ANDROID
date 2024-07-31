package com.talkable.presentation.mypage.saved

import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentSavedWordBinding
import com.talkable.presentation.mypage.saved.SavedFragment.Companion.NO_CHIP_SELECTED
import com.talkable.presentation.mypage.saved.model.SavedListModel
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedWordFragment :
    BindingFragment<FragmentSavedWordBinding>(R.layout.fragment_saved_word) {
    override fun initView() {
        initSavedWordAdapter()
        initSavedWordChipClickListener()
    }

    private fun initSavedWordAdapter() {
        with(binding.rvSavedWord) {
            layoutManager = LinearLayoutManager(context)
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

    companion object {
        val mockData = SavedListModel(
            savedWordId = 1,
            savedWordList = listOf(
                SavedWord(
                    type = 0,
                    word = "public holiday",
                    translation = "공휴일",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 1,
                    word = "fair",
                    translation = "1. 타당한, 온당한 \n2. 공정한, 공평한",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 2,
                    word = "candid",
                    translation = "솔직한",
                    tag = "암기 중"
                )
            )
        )

        val difficultWord = SavedListModel(
            savedWordId = 2,
            savedWordList = listOf(
                SavedWord(
                    type = 0,
                    word = "public holiday",
                    translation = "공휴일",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 0,
                    word = "public holiday",
                    translation = "공휴일",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 0,
                    word = "public holiday",
                    translation = "공휴일",
                    tag = "어려워요"
                )
            )
        )

        val memorizingWord = SavedListModel(
            savedWordId = 3,
            savedWordList = listOf(
                SavedWord(
                    type = 1,
                    word = "fair",
                    translation = "1. 타당한, 온당한 \n2. 공정한, 공평한",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 1,
                    word = "fair",
                    translation = "1. 타당한, 온당한 \n2. 공정한, 공평한",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 1,
                    word = "fair",
                    translation = "1. 타당한, 온당한 \n2. 공정한, 공평한",
                    tag = "외웠어요"
                )
            )
        )

        val memorizedWord = SavedListModel(
            savedWordId = 4,
            savedWordList = listOf(
                SavedWord(
                    type = 2,
                    word = "candid",
                    translation = "솔직한",
                    tag = "암기 중"
                ),
                SavedWord(
                    type = 2,
                    word = "candid",
                    translation = "솔직한",
                    tag = "암기 중"
                ),
                SavedWord(
                    type = 2,
                    word = "candid",
                    translation = "솔직한",
                    tag = "암기 중"
                )
            )
        )
    }
}