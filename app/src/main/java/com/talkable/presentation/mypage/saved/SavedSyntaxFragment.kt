package com.talkable.presentation.mypage.saved

import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentSavedSyntaxBinding
import com.talkable.presentation.mypage.saved.SavedFragment.Companion.NO_CHIP_SELECTED
import com.talkable.presentation.mypage.saved.model.SavedListModel
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxFragment :
    BindingFragment<FragmentSavedSyntaxBinding>(R.layout.fragment_saved_syntax) {
    override fun initView() {
        initSavedSyntaxAdapter()
        initSavedSyntaxChipClickListener()
    }

    private fun initSavedSyntaxAdapter() {
        with(binding.rvSavedWord) {
            layoutManager = LinearLayoutManager(context)
            adapter = SavedSyntaxAdapter(mockData.savedWordList)
        }
    }

    private fun initSavedSyntaxChipClickListener() {
        binding.layoutSavedSort.cgSavedList.setOnCheckedStateChangeListener { chipGroup, ints ->
            when (chipGroup.checkedChipId) {
                NO_CHIP_SELECTED -> {
                    initSavedSyntaxAdapter()
                }

                R.id.chip_saved_difficult -> {
                    with(binding.rvSavedWord) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SavedSyntaxAdapter(difficultWord.savedWordList)
                    }
                }

                R.id.chip_saved_memorizing -> {
                    with(binding.rvSavedWord) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SavedSyntaxAdapter(memorizingWord.savedWordList)
                    }
                }

                R.id.chip_saved_memorized -> {
                    with(binding.rvSavedWord) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = SavedSyntaxAdapter(memorizedWord.savedWordList)
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
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 1,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 2,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "암기 중"
                )
            )
        )

        val difficultWord = SavedListModel(
            savedWordId = 2,
            savedWordList = listOf(
                SavedWord(
                    type = 0,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 0,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 0,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "어려워요"
                )
            )
        )

        val memorizingWord = SavedListModel(
            savedWordId = 3,
            savedWordList = listOf(
                SavedWord(
                    type = 1,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 1,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 1,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "외웠어요"
                )
            )
        )

        val memorizedWord = SavedListModel(
            savedWordId = 4,
            savedWordList = listOf(
                SavedWord(
                    type = 2,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "암기 중"
                ),
                SavedWord(
                    type = 2,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "암기 중"
                ),
                SavedWord(
                    type = 2,
                    word = "What did you do today in school?",
                    translation = "오늘 학교에서 무엇을 했니?",
                    tag = "암기 중"
                )
            )
        )
    }
}