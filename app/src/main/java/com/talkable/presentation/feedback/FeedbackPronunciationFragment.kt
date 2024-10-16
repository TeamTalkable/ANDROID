package com.talkable.presentation.feedback

import android.media.MediaPlayer
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import kotlinx.coroutines.launch

class FeedbackPronunciationFragment :
    BindingFragment<FragmentFeedbackPronunciationBinding>(R.layout.fragment_feedback_pronunciation) {

    private var englishWord: String? = null
    private val viewModel: FeedbackViewModel by activityViewModels()

    override fun initView() {
        statusBarColorOf(R.color.white)
        collect()
        binding.bnvFeedbackPronunciation.itemIconTintList = null
        initBottomNavigationItemClickListener()
        initRecordCancelClickListener()
        initRecordCheckClickListener()
        initNavigateToBackClickListener()
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FeedbackUiState.PatchPronunciationFeedbacks -> {
                        binding.tvFeedbackPronunciationEnglish.text = uiState.answer
                        binding.tvFeedbackPronunciationAccuracy.text = "${uiState.score}%"
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun initNavigateToBackClickListener() {
        binding.appBarTalkFeedbackExpression.ivAppBarBack.setOnClickListener {
            findNavController().popBackStack()
        }
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
        playerScience: MediaPlayer, playerToday: MediaPlayer,
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
        const val PRONUNCIATION_DIALOG = "pronunciationDialog"
    }
}