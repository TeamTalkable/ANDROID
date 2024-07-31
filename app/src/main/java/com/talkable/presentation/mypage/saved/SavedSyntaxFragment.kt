package com.talkable.presentation.mypage.saved

import androidx.recyclerview.widget.LinearLayoutManager
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentSavedSyntaxBinding
import com.talkable.presentation.mypage.saved.SavedFragment.Companion.NO_CHIP_SELECTED
import com.talkable.presentation.mypage.saved.model.SavedListModel
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxFragment :
    BindingFragment<FragmentSavedSyntaxBinding>(R.layout.fragment_saved_syntax) {
    override fun initView() {
        statusBarColorOf(R.color.main_3)
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
                    word = "How was your day?",
                    translation = "오늘 하루 어땠어?",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 1,
                    word = "Have you finished your homework?",
                    translation = "숙제 다 했니?",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 2,
                    word = "What did you eat for lunch?",
                    translation = "점심으로 뭐 먹었어?",
                    tag = "암기 중"
                )
            )
        )

        val difficultWord = SavedListModel(
            savedWordId = 2,
            savedWordList = listOf(
                SavedWord(
                    type = 0,
                    word = "Can you help me with this problem?",
                    translation = "이 문제 좀 도와줄 수 있어?",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 0,
                    word = "This is too difficult for me.",
                    translation = "이건 나에게 너무 어려워.",
                    tag = "어려워요"
                ),
                SavedWord(
                    type = 0,
                    word = "I don't understand this topic.",
                    translation = "이 주제를 이해할 수 없어.",
                    tag = "어려워요"
                )
            )
        )

        val memorizingWord = SavedListModel(
            savedWordId = 3,
            savedWordList = listOf(
                SavedWord(
                    type = 1,
                    word = "I'm trying to remember this.",
                    translation = "이걸 기억하려고 노력 중이야.",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 1,
                    word = "I've been studying hard.",
                    translation = "열심히 공부하고 있어.",
                    tag = "외웠어요"
                ),
                SavedWord(
                    type = 1,
                    word = "I need to practice more.",
                    translation = "더 연습해야 돼.",
                    tag = "외웠어요"
                )
            )
        )

        val memorizedWord = SavedListModel(
            savedWordId = 4,
            savedWordList = listOf(
                SavedWord(
                    type = 2,
                    word = "I remember this perfectly.",
                    translation = "이걸 완벽하게 기억해.",
                    tag = "암기 중"
                ),
                SavedWord(
                    type = 2,
                    word = "This is easy for me now.",
                    translation = "이제 이건 나에게 쉬워.",
                    tag = "암기 중"
                ),
                SavedWord(
                    type = 2,
                    word = "I have mastered this.",
                    translation = "이걸 완전히 익혔어.",
                    tag = "암기 중"
                )
            )
        )
    }
}