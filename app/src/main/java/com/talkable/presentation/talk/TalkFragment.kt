package com.talkable.presentation.talk

import android.graphics.Paint
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.context.pxToDp
import com.talkable.databinding.FragmentTalkBinding
import com.talkable.presentation.firstTalk
import kotlin.random.Random

class TalkFragment : BindingFragment<FragmentTalkBinding>(R.layout.fragment_talk) {

    private var clickCount = FIRST_CLICK

    override fun initView() {
        initGuideLayoutVisible()
        initAppbarCancelClickListener()
        initGuideLayoutClickListener()
        setRandomVideo()
        initBottomSheet()
        initListenBtnClickListener()
        initTranslateBtnClickListener()
        initShowBtnClickListener()
        initSpeakBtnClickListener()
        initHintTextViewClickListener()
        initTalkAdapter()
        initSpeakCompleteBtnClickListener()
        initTalkStackBtnClickListener()
    }

    private fun initGuideLayoutVisible() {
        with(binding.includeLayoutTalkGuide) {
            if (firstTalk) {
                layoutTalkGuide.visibility = VISIBLE
            } else {
                layoutTalkGuide.visibility = GONE
            }
        }
    }

    private fun initAppbarCancelClickListener() {
        binding.btnTalkClose.setOnClickListener {
            navigateToTotalTalkFeedback()
        }
    }

    private fun navigateToTotalTalkFeedback() =
        findNavController().navigate(R.id.action_talk_to_talk_feedback)

    private fun initSpeakCompleteBtnClickListener() {
        binding.includeLayoutTalkSpeech.ivTalkSpeech.setOnClickListener {
            navigateToFeedbackLoadingFragment()
        }
    }

    private fun navigateToSavedFeedback() =
        findNavController().navigate(R.id.action_talk_to_today_saved)

    private fun initTalkStackBtnClickListener() {
        binding.btnTalkStack.setOnClickListener {
            navigateToSavedFeedback()
        }
    }

    private fun navigateToFeedbackLoadingFragment() =
        findNavController().navigate(R.id.action_talk_to_feedback_loading)

    // 어댑터 연결
    private fun initTalkAdapter() {
        binding.layoutBottomSheetTalk.rvBottomSheet.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TalkAdapter().apply {
                submitList(talkMockData.toMutableList())
            }
        }
    }

    // 코치 마크 레이아웃
    private fun initGuideLayoutClickListener() {
        with(binding.includeLayoutTalkGuide) {
            layoutTalkGuide.setOnClickListener {
                if (firstTalk) {
                    layoutTalkGuide.visibility = GONE
                    firstTalk = false
                }
            }
        }
    }

    // 바텀 시트
    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutBottomSheetTalk.root)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.expandedOffset = requireContext().pxToDp(80)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    // 하단 상태일 때 버튼 숨김
                    STATE_COLLAPSED -> {
                        val customColor =
                            with(binding.layoutBottomSheetTalk) {
                                btnBottomSheetSelectArea.visibility = GONE
                            }
                    }
                    // 바텀 시트가 완전히 펼쳐졌을 때 버튼 보이게
                    STATE_EXPANDED -> {
                        with(binding.layoutBottomSheetTalk) {
                            btnBottomSheetSelectArea.visibility = VISIBLE
                        }
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    // 듣기 버튼 클릭
    private fun initListenBtnClickListener() {
        with(binding) {
            btnTalkListen.setOnClickListener {
                btnTalkListen.isSelected = !binding.btnTalkListen.isSelected
                binding.videoViewTalkBackground.start()
            }
        }
    }

    // 비디오 랜덤 적용
    private fun setRandomVideo() {
        val videoResources = arrayOf(
            R.raw.bg_talk_school,
            R.raw.bg_talk_classroom,
            R.raw.bg_talk_library
        )

        val randomVideoResource = videoResources[Random.nextInt(videoResources.size)]
        val videoPath = "android.resource://${requireContext().packageName}/$randomVideoResource"
        val videoUri = Uri.parse(videoPath)

        binding.videoViewTalkBackground.setVideoURI(videoUri)
        binding.videoViewTalkBackground.start()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.videoViewTalkBackground.pause()
        }, 500)
    }

    // 번역 버튼 클릭
    private fun initTranslateBtnClickListener() {
        with(binding) {
            btnTalkTranslate.setOnClickListener {
                btnTalkTranslate.isSelected = !binding.btnTalkTranslate.isSelected
                tvTalkTranslate.isVisible = !binding.tvTalkTranslate.isVisible
                initShowListenTextView()
            }
        }
    }

    // 보기 버튼 클릭
    private fun initShowBtnClickListener() {
        with(binding) {
            btnTalkShow.setOnClickListener {
                btnTalkShow.isSelected = !binding.btnTalkShow.isSelected
                tvTalkText.isVisible = !binding.tvTalkText.isVisible
                initShowListenTextView()
            }
        }
    }

    // listen 텍스트 보여주기
    private fun initShowListenTextView() {
        with(binding) {
            tvTalkListen.isVisible = !(btnTalkTranslate.isSelected || btnTalkShow.isSelected)
        }
    }

    // 말하기 버튼 클릭
    private fun initSpeakBtnClickListener() {
        with(binding) {
            btnTalkSpeak.setOnClickListener {
                includeLayoutTalkSpeech.layoutTalkSpeech.visibility = VISIBLE
                btnTalkSpeak.visibility = GONE
                includeBottomSheetTalk.visibility = GONE
                tvTalkHint.visibility = GONE
            }
        }
    }

    // 힌트 클릭
    private fun initHintTextViewClickListener() {
        with(binding) {
            tvTalkHint.setOnClickListener {
                when (clickCount) {
                    FIRST_CLICK -> {
                        HintToast.createToast(
                            requireActivity(),
                            getString(R.string.hint_talk),
                            getString(R.string.tv_talk_content_hint)
                        )?.show()
                        changeHintText()
                    }

                    else -> {
                        includeTalkToastExample.viewTalkToastExample.visibility = VISIBLE
                    }
                }
                clickCount++
            }
        }
    }

    //3초 뒤 텍스트 변경
    private fun changeHintText() {
        Handler(Looper.getMainLooper()).postDelayed({
            with(binding) {
                tvTalkHint.paintFlags = Paint.UNDERLINE_TEXT_FLAG // 밑줄
                binding.tvTalkHint.text = getString(R.string.hint_talk_example)
            }
        }, 3000)
    }

    companion object {
        const val FIRST_CLICK = 0

        // 더미 데이터
        val talkMockData = listOf(
            TalkData(type = "ai", message = "What did you do today in scool?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I learn about photosynthesis."),
            TalkData(type = "ai", message = "What did you do today in scool?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I learn about photosynthesis."),
            TalkData(type = "ai", message = "What did you do today in scool?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I learn about photosynthesis."),
        )
    }
}