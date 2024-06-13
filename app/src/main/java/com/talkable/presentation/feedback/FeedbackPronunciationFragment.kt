package com.talkable.presentation.feedback

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import com.talkable.presentation.FeedbackTextColor
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
        setAppBar()
        initAppbarBackClickListener()
    }

    private fun initPronunciationWordAdapter() {
        binding.rvFeedbackPronunciation.adapter =
            FeedbackPronunciationWordAdapter(
                requireContext(),
                onClickWordItem = { data, isSelected ->
                    handleWordItemClick(data, isSelected)
                }).apply {
                submitList(mockData.learnedPronunciation)
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
        val invisibleIfSelected = if (!isSelected) View.VISIBLE else View.INVISIBLE

        tvFeedbackPronunciationEnglish.isVisible = isSelected
        tvFeedbackPronunciationKorean.isVisible = isSelected
        tvFeedbackPronunciationEnglishSmall.visibility = invisibleIfSelected
        tvFeedbackPronunciationEnglishWord.visibility = invisibleIfSelected
        tvFeedbackPronunciationWordKorean.isVisible = !isSelected
        tvFeedbackPronunciationEnglishWordPronun.visibility = invisibleIfSelected

        if (isSelected) {
            tvFeedbackPronunciationAccuracy.text = mockData.accuracy + "%"
            pbFeedbackPronunciation.progress = mockData.accuracy.toInt()
        }
    }

    private fun setAfterAnswerTextColor(fullText: String, partsText: List<String>) {
        val spannableString =
            FeedbackTextColor(requireContext()).setAfterAnswerTextColor(fullText, partsText)

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

    private fun initAppbarBackClickListener() {
        binding.appBarTalkFeedbackExpression.ivAppBarBack.setOnClickListener {
            navigateToBack()
        }
    }

    private fun navigateToBack() = findNavController().popBackStack()

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
            FeedbackPronunciationCompleteDialog().show(childFragmentManager, PRONUNCIATION_DIALOG)
        }
    }

    private fun setAppBar() {
        binding.appBarTalkFeedbackExpression.leftBackIsVisible = true
        binding.appBarTalkFeedbackExpression.rightBackIsVisible = false
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

        const val PRONUNCIATION_DIALOG = "pronunciationDialog"
    }
}