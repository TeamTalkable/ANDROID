package com.talkable.presentation.feedback

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import com.talkable.presentation.FeedbackTextColor
import com.talkable.presentation.feedback.model.FeedbackPronunciationModel
import com.talkable.presentation.talk.feedback.model.Learned
import timber.log.Timber

class FeedbackPronunciationFragment :
    BindingFragment<FragmentFeedbackPronunciationBinding>(R.layout.fragment_feedback_pronunciation) {

    private var englishWord: String? = null
    private lateinit var nextQuestionEn: String
    private lateinit var nextQuestionKo: String
    private lateinit var feedbackAfter: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nextQuestionEn = arguments?.getString(Key.FEEDBACK_QUESTION_EN).orEmpty()
        nextQuestionKo = arguments?.getString(Key.FEEDBACK_QUESTION_KO).orEmpty()
        feedbackAfter = arguments?.getString(Key.FEEDBACK_AFTER).orEmpty()
    }

    override fun initView() {
        statusBarColorOf(R.color.white)
        binding.bnvFeedbackPronunciation.itemIconTintList = null
        binding.model = mockData
        setAfterAnswerTextColor(mockData.fullAnswer, mockData.answerParts)
        initPronunciationWordAdapter()
        initBottomNavigationItemClickListener()
        initRecordCancelClickListener()
        initRecordCheckClickListener()
        initAppbarBackClickListener()
        initNavigateToTalkBtnClickListener()
    }

    private fun initNavigateToTalkBtnClickListener() {
        binding.btnFeedbackPronunciationToTalk.setOnClickListener {
            navigateToTalkFragment()
        }
    }

    private fun navigateToTalkFragment() = findNavController().navigate(
        R.id.fragment_talk, bundleOf(
            Key.FEEDBACK_QUESTION_EN to nextQuestionEn,
            Key.FEEDBACK_QUESTION_KO to nextQuestionKo,
            Key.FEEDBACK_AFTER to feedbackAfter
        )
    )

    private fun initPronunciationWordAdapter() {
        binding.rvFeedbackPronunciation.adapter = FeedbackPronunciationWordAdapter(
            requireContext(),
            onClickWordItem = { data, isSelected ->
                handleWordItemClick(data, isSelected)
                englishWord = if (!isSelected) data.englishWord else null
                Timber.e(englishWord.toString())
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
        val mediaScienceUserPlayer = MediaPlayer.create(binding.root.context, R.raw.science_user)
        val mediaTodayUserPlayer = MediaPlayer.create(binding.root.context, R.raw.today_user)
        val mediaSciencePlayer = MediaPlayer.create(binding.root.context, R.raw.science)
        val mediaTodayPlayer = MediaPlayer.create(binding.root.context, R.raw.today)

        binding.bnvFeedbackPronunciation.setOnItemSelectedListener {
            if (englishWord != null) when (it.itemId) {
                R.id.mick -> handleMickItemEvent(true)
                R.id.ai_voice -> {
                    binding.bnvFeedbackPronunciation.itemIconTintList =
                        ContextCompat.getColorStateList(
                            binding.root.context, R.color.sel_quiz_flash_icon
                        )
                    initVoiceBtnClickListener(mediaSciencePlayer, mediaTodayPlayer)
                }

                R.id.user_voice -> {
                    binding.bnvFeedbackPronunciation.itemIconTintList =
                        ContextCompat.getColorStateList(
                            binding.root.context, R.color.sel_quiz_flash_icon
                        )
                    initVoiceBtnClickListener(
                        mediaScienceUserPlayer, mediaTodayUserPlayer
                    )
                }

                else -> Unit
            }
            true
        }

    }

    private fun initVoiceBtnClickListener(
        playerScience: MediaPlayer, playerToday: MediaPlayer
    ) {
        val mediaPlayer = if (englishWord == "science") {
            playerScience
        } else {
            playerToday
        }

        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            binding.bnvFeedbackPronunciation.itemIconTintList = null
        }
    }

    private fun initAppbarBackClickListener() {
        binding.btnFeedbackPronunciationBack.setOnClickListener {
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
                ), Learned.Pronunciation(
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