package com.talkable.presentation.feedback

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.view.isVisible
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.colorOf
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import com.talkable.presentation.feedback.model.FeedbackPronunciationModel
import com.talkable.presentation.talk.feedback.model.Learned

class FeedbackPronunciationFragment :
    BindingFragment<FragmentFeedbackPronunciationBinding>(R.layout.fragment_feedback_pronunciation) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.model = mockData
        setAfterAnswerTextColor(mockData.fullAnswer, mockData.answerParts)
        initPronunciationWordAdapter()
        initBottomNavigationItemClickListener()
        initRecordCancelClickListener()
        initRecordCheckClickListener()
    }

    private fun initPronunciationWordAdapter() {
        var selectedPosition = -1
        binding.rvFeedbackPronunciation.adapter =
            FeedbackPronunciationWordAdapter(
                requireContext(),
                onClickWordItem = { data, isSelected, position ->
                    handleWordItemClick(data, isSelected)
                    selectedPosition = position
                }).apply {
                submitList(mockData.learnedPronunciation)
                selectItem(selectedPosition)
            }
    }

    private fun handleWordItemClick(data: Learned.Pronunciation, isSelected: Boolean) =
        with(binding) {
            tvFeedbackPronunciationEnglishWord.text = data.englishWord
            tvFeedbackPronunciationEnglishWordPronun.text = data.pronunciationEnglish
            tvFeedbackPronunciationWordKorean.text = data.koreanWord
            tvFeedbackPronunciationAccuracy.text = "${data.wordAccuracy}%"
            pbFeedbackPronunciation.progress = data.wordAccuracy.orEmpty().toInt()
            handleViewVisibility(isSelected)
        }

    private fun handleViewVisibility(isSelected: Boolean) = with(binding) {
        tvFeedbackPronunciationEnglish.isVisible = isSelected
        tvFeedbackPronunciationKorean.isVisible = isSelected
        tvFeedbackPronunciationEnglishSmall.visibility =
            if (!isSelected) View.VISIBLE else View.INVISIBLE
        tvFeedbackPronunciationEnglishWord.visibility =
            if (!isSelected) View.VISIBLE else View.INVISIBLE
        tvFeedbackPronunciationWordKorean.isVisible = !isSelected
        if (isSelected) {
            tvFeedbackPronunciationAccuracy.text = mockData.accuracy + "%"
            pbFeedbackPronunciation.progress = mockData.accuracy.toInt()
        }
        tvFeedbackPronunciationEnglishWordPronun.visibility =
            if (!isSelected) View.VISIBLE else View.INVISIBLE
    }

    private fun setAfterAnswerTextColor(fullText: String, partsText: List<String>) {
        val spannableString = SpannableString(fullText)

        partsText.forEach { part ->
            val startIndex = fullText.indexOf(part)
            if (startIndex != -1) {
                val endIndex = startIndex + part.length
                val span = ForegroundColorSpan(colorOf(R.color.main_4))
                spannableString.setSpan(
                    span,
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        binding.tvFeedbackPronunciationEnglish.text = spannableString
        binding.tvFeedbackPronunciationEnglishSmall.text = spannableString
    }

    private fun initBottomNavigationItemClickListener() {
        binding.bnvFeedbackPronunciation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mick -> handleMickItemEvent(true)
                else -> Unit
            }
            true
        }
    }

    private fun handleMickItemEvent(isSelected: Boolean) {
        binding.layoutFeedbackPronunciationMick.isVisible = isSelected
        binding.bnvFeedbackPronunciation.visibility =
            if (isSelected) View.INVISIBLE else View.VISIBLE
    }

    private fun initRecordCancelClickListener() {
        binding.ivFeedbackPronunciatoinTrash.setOnClickListener {
            handleMickItemEvent(false)
        }
    }

    private fun initRecordCheckClickListener() {
        binding.ivFeedbackPronunciationCheck.setOnClickListener {
            handleMickItemEvent(false)
        }
    }

    companion object {
        val mockData = FeedbackPronunciationModel(
            feedbackId = 3,
            fullAnswer = "I took a science class today. And I  took a math class too.\nI took a science class today.",
            answerParts = listOf("took", "science", "math", "too", "today"),
            accuracy = "80",
            korean = "나는 오늘 과학 수업을 들었다. 그리고 수학 수업도 들었다.",
            learnedPronunciation = listOf(
                Learned.Pronunciation(
                    type = "발음",
                    englishWord = "science",
                    pronunciationEnglish = "[ ˈsaɪəns ]",
                    koreanWord = "과학",
                    wordAccuracy = "70"
                ),
                Learned.Pronunciation(
                    type = "발음",
                    englishWord = "today",
                    pronunciationEnglish = "[ ˈtodai ]",
                    koreanWord = "오늘",
                    wordAccuracy = "50"
                )
            ),
        )
    }
}