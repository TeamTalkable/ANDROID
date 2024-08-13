package com.talkable.presentation.mypage.saved

import android.os.Bundle
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentSavedSyntaxBinding
import com.talkable.presentation.mypage.saved.Constants.NO_CHIP_SELECTED
import com.talkable.presentation.mypage.saved.model.SavedListModel
import com.talkable.presentation.mypage.saved.model.SavedWord

class SavedSyntaxFragment :
    BindingFragment<FragmentSavedSyntaxBinding>(R.layout.fragment_saved_syntax) {

    private lateinit var savedSyntaxAdapter: SavedSyntaxAdapter

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        initSavedSyntaxAdapter()
        initSavedSyntaxChipClickListener()
        initTranslationBtnClickListener()
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

    private fun initSavedSyntaxAdapter() {
        savedSyntaxAdapter = SavedSyntaxAdapter()
        with(binding.rvSavedSyntax) {
            adapter = savedSyntaxAdapter
        }
        savedSyntaxAdapter.submitList(mockData.savedWordList)
    }

    private fun initSavedSyntaxChipClickListener() {
        binding.layoutSavedSort.cgSavedList.setOnCheckedStateChangeListener { chipGroup, _ ->
            val newData = when (chipGroup.checkedChipId) {
                NO_CHIP_SELECTED -> mockData.savedWordList
                R.id.chip_saved_difficult -> difficultSyntax.savedWordList
                R.id.chip_saved_memorizing -> memorizingSyntax.savedWordList
                R.id.chip_saved_memorized -> memorizedSyntax.savedWordList
                else -> emptyList()
            }
            savedSyntaxAdapter.submitList(newData)
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

    private val mockData = SavedListModel(
        savedWordId = 1, savedWordList = listOf(
            SavedWord(
                type = 0, word = "How was your day?", translation = "오늘 하루 어땠어?", tag = "어려워요"
            ), SavedWord(
                type = 1,
                word = "Have you finished your homework?",
                translation = "숙제 다 했니?",
                tag = "외웠어요"
            ), SavedWord(
                type = 2,
                word = "What did you eat for lunch?",
                translation = "점심으로 뭐 먹었어?",
                tag = "암기 중"
            )
        )
    )

    private val difficultSyntax = SavedListModel(
        savedWordId = 2, savedWordList = listOf(
            SavedWord(
                type = 0,
                word = "Can you help me with this problem?",
                translation = "이 문제 좀 도와줄 수 있어?",
                tag = "어려워요"
            ), SavedWord(
                type = 0,
                word = "This is too difficult for me.",
                translation = "이건 나에게 너무 어려워.",
                tag = "어려워요"
            ), SavedWord(
                type = 0,
                word = "I don't understand this topic.",
                translation = "이 주제를 이해할 수 없어.",
                tag = "어려워요"
            )
        )
    )

    private val memorizingSyntax = SavedListModel(
        savedWordId = 3, savedWordList = listOf(
            SavedWord(
                type = 1,
                word = "I'm trying to remember this.",
                translation = "이걸 기억하려고 노력 중이야.",
                tag = "외웠어요"
            ), SavedWord(
                type = 1,
                word = "I've been studying hard.",
                translation = "열심히 공부하고 있어.",
                tag = "외웠어요"
            ), SavedWord(
                type = 1, word = "I need to practice more.", translation = "더 연습해야 돼.", tag = "외웠어요"
            )
        )
    )

    private val memorizedSyntax = SavedListModel(
        savedWordId = 4, savedWordList = listOf(
            SavedWord(
                type = 2,
                word = "I remember this perfectly.",
                translation = "이걸 완벽하게 기억해.",
                tag = "암기 중"
            ), SavedWord(
                type = 2,
                word = "This is easy for me now.",
                translation = "이제 이건 나에게 쉬워.",
                tag = "암기 중"
            ), SavedWord(
                type = 2, word = "I have mastered this.", translation = "이걸 완전히 익혔어.", tag = "암기 중"
            )
        )
    )
}