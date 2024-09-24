package com.talkable.presentation.talk

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
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
    private lateinit var speechRecognizer: SpeechRecognizer

    override fun initView() {
        initGuideLayoutVisible()
        initTalkFeedbackVisible()
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
        initFeedbackListenBtnClickListener()
        initFeedbackTranslateBtnClickListener()
        initFeedbackCloseBtnClickListener()
        initSTT()
    }

    // STT 초기화 및 리스너 설정
    private fun initSTT() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            // 음성 입력 종료
            override fun onEndOfSpeech() {
                binding.includeLayoutTalkSpeech.layoutTalkSpeech.visibility = VISIBLE
            }
            override fun onError(error: Int) {
                binding.includeLayoutTalkSpeech.tvTalkUserSpeech.setText(R.string.error_talk_retry)
            }
            // STT 결과 화면에 표시
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    binding.includeLayoutTalkSpeech.tvTalkUserSpeech.setText(matches[0])
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
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

    private fun initTalkFeedbackVisible() {
        if (!firstTalk) {
            showFeedbackRetryDialog()
            delayedTalkFeedbackDialog()
        }
    }

    private fun showFeedbackRetryDialog() {
        FeedbackRetryDialog().show(
            childFragmentManager, TALK_DIALOG
        )
    }

    private fun delayedTalkFeedbackDialog() {
        Handler(Looper.getMainLooper()).postDelayed({
            showTalkFeedbackDialog()
        }, 2000)
    }

    private fun showTalkFeedbackDialog() {
        binding.includeLayoutTalkFeedback.layoutFeedbackTalk.visibility = VISIBLE
    }

    private fun initFeedbackListenBtnClickListener() {
        with(binding.includeLayoutTalkFeedback) {
            btnFeedbackTalkListen.setOnClickListener {
                btnFeedbackTalkListen.isSelected = !btnFeedbackTalkListen.isSelected
            }
        }
    }

    private fun initFeedbackTranslateBtnClickListener() {
        with(binding.includeLayoutTalkFeedback) {
            btnFeedbackTalkTranslate.setOnClickListener {
                btnFeedbackTalkTranslate.isSelected = !btnFeedbackTalkTranslate.isSelected
                tvFeedbackTalkTranslate.isVisible = !tvFeedbackTalkTranslate.isVisible
            }
        }
    }

    private fun initFeedbackCloseBtnClickListener() {
        with(binding.includeLayoutTalkFeedback) {
            btnFeedbackTalkClose.setOnClickListener {
                layoutFeedbackTalk.visibility = GONE
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
        binding.btnTalkFolder.setOnClickListener {
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
                btnTalkListen.isSelected = !btnTalkListen.isSelected
                videoViewTalkBackground.start()
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
        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/$randomVideoResource")

        with(binding) {
            videoViewTalkBackground.setVideoURI(videoUri)
            videoViewTalkBackground.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true  // 비디오 반복 재생
                mediaPlayer.setVolume(0f, 0f)  // 비디오 음소거
                mediaPlayer.seekTo(1)  // 첫 프레임에서 정지
            }
        }
    }

    // 번역 버튼 클릭
    private fun initTranslateBtnClickListener() {
        with(binding) {
            btnTalkTranslate.setOnClickListener {
                btnTalkTranslate.isSelected = !btnTalkTranslate.isSelected
                tvTalkTranslate.isVisible = !tvTalkTranslate.isVisible
                initShowListenTextView()
            }
        }
    }

    // 보기 버튼 클릭
    private fun initShowBtnClickListener() {
        with(binding) {
            btnTalkShow.setOnClickListener {
                btnTalkShow.isSelected = !btnTalkShow.isSelected
                tvTalkText.isVisible = !tvTalkText.isVisible
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
                btnTalkSpeak.visibility = GONE
                includeBottomSheetTalk.visibility = GONE
                tvTalkHint.visibility = GONE

                startSpeechRecognition()
            }
        }
    }

    // 음성 인식
    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US")
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "en-US")
        }
        speechRecognizer.startListening(intent)
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

    //2초 뒤 텍스트 변경
    private fun changeHintText() {
        Handler(Looper.getMainLooper()).postDelayed({
            with(binding) {
                tvTalkHint.paintFlags = Paint.UNDERLINE_TEXT_FLAG // 밑줄
                tvTalkHint.text = getString(R.string.hint_talk_example)
            }
        }, 2000)
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
        const val TALK_DIALOG = "talkDialog"
    }
}