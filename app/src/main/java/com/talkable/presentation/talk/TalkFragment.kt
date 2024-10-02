package com.talkable.presentation.talk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.FEEDBACK_AFTER
import com.talkable.core.util.Key.FEEDBACK_BEFORE
import com.talkable.core.util.Key.FEEDBACK_QUESTION_EN
import com.talkable.core.util.Key.FEEDBACK_QUESTION_KO
import com.talkable.core.util.context.pxToDp
import com.talkable.core.util.fragment.stringOf
import com.talkable.core.util.fragment.toast
import com.talkable.databinding.FragmentTalkBinding
import com.talkable.presentation.firstTalk
import timber.log.Timber
import java.util.Locale

class TalkFragment : BindingFragment<FragmentTalkBinding>(R.layout.fragment_talk),
    TextToSpeech.OnInitListener {

    private var clickCount = FIRST_CLICK
    private lateinit var speechRecognizer: SpeechRecognizer
    private var tts: TextToSpeech? = null
    private lateinit var nextQuestionEn: String
    private lateinit var nextQuestionKo: String
    private lateinit var feedbackAfter: String

    private var guideClickCount = FIRST_CLICK
    private var textIndex = 0
    private val englishGuideTextArray =
        arrayOf(R.string.tv_talk_second_en, R.string.tv_talk_third_en)
    private val koreanGuideTextArray =
        arrayOf(R.string.tv_talk_second_kr, R.string.tv_talk_third_kr)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nextQuestionEn =
            arguments?.getString(FEEDBACK_QUESTION_EN) ?: stringOf(R.string.tv_talk_english)
        nextQuestionKo =
            arguments?.getString(FEEDBACK_QUESTION_KO) ?: stringOf(R.string.tv_talk_korean)
        feedbackAfter =
            arguments?.getString(FEEDBACK_AFTER).orEmpty()
    }

    override fun initView() {
        initTalkGuide()
        initTalkFeedbackVisible()
        initAppbarCancelClickListener()
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
        initializeSpeechRecognizer()
        tts = TextToSpeech(requireContext(), this) // TTS 초기화
    }

    private fun initTalkGuide() {
        if (guideClickCount == FIRST_CLICK) {
            with(binding) {
                root.setOnClickListener {
                    if (guideClickCount < MAX_GUIDE_CLICK) {
                        tvTalkEnglish.text = getString(englishGuideTextArray[textIndex])
                        tvTalkTranslate.text = getString(koreanGuideTextArray[textIndex])
                        textIndex = (textIndex + 1) % englishGuideTextArray.size
                        guideClickCount++
                    } else {
                        btnTalkSpeak.isEnabled = true
                        tvTalkEnglish.isVisible = false
                        tvTalkTranslate.isVisible = false
                        tvTalkListen.isVisible = true
                        setQuestionLayout()
                    }
                }
            }
        }
    }

    private fun setQuestionLayout() = with(binding) {
        tvTalkEnglish.text = nextQuestionEn
        tvTalkTranslate.text = nextQuestionKo
        includeLayoutTalkFeedback.tvFeedbackTalkSentence.text = feedbackAfter
        groupTalkBtn.isVisible = true
    }

    // STT 초기화
    private fun initializeSpeechRecognizer() {
        try {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
            speechRecognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}

                // 음성 입력 종료
                override fun onEndOfSpeech() {
                    with(binding) {
                        includeLayoutTalkSpeech.layoutTalkSpeech.isVisible = true
                        btnTalkSpeak.isVisible = false
                        tvTalkHint.isVisible = false
                        includeBottomSheetTalk.isVisible = false
                    }
                }

                override fun onError(error: Int) {
                    binding.includeLayoutTalkSpeech.etTalkUserSpeech.setText(R.string.error_talk_retry)
                }

                // STT 결과 화면에 표시
                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        binding.includeLayoutTalkSpeech.etTalkUserSpeech.setText(matches[0])
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        } catch (e: Exception) {
            Timber.d("STT 초기화 중 오류 발생: ${e.message}")
        }
        tts = TextToSpeech(requireContext(), this) // TTS 초기화
    }

    // TTS 초기화 완료 시 호출
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        } else {
            Timber.d("TTS 초기화 실패")
        }
    }

    private fun initListenBtnClickListener() {
        with(binding) {
            btnTalkListen.setOnClickListener {
                btnTalkListen.isSelected = !btnTalkListen.isSelected

                if (btnTalkListen.isSelected) {
                    videoViewTalkBackground.start()

                    // TTS 시작
                    val params = Bundle().apply {
                        putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "tts1")
                    }
                    tts?.speak(
                        binding.tvTalkEnglish.text.toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        params,
                        "tts1"
                    )
                } else {
                    // 비디오 중지
                    videoViewTalkBackground.pause()
                    videoViewTalkBackground.seekTo(1)  // 첫 프레임으로 돌아감
                }

                // TTS 진행 상태 추적
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String) {}
                    override fun onDone(utteranceId: String) {
                        if (utteranceId == "tts1") {
                            requireActivity().runOnUiThread {
                                videoViewTalkBackground.pause()  // 비디오 일시정지
                                videoViewTalkBackground.seekTo(1)  // 첫 프레임으로 돌아가기
                                btnTalkListen.isSelected = false  // 버튼 상태 초기화
                            }
                        }
                    }

                    override fun onError(utteranceId: String) {
                        requireActivity().runOnUiThread {
                            Timber.d("TTS 오류 발생")
                        }
                    }
                })
            }
        }
    }

    // TTS 리소스 해제
    override fun onDestroyView() {
        super.onDestroyView()
        tts?.stop()
        tts?.shutdown()
        tts = null
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

                if (btnFeedbackTalkListen.isSelected) {
                    val params = Bundle().apply {
                        putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "feedback_tts")
                    }
                    tts?.speak(
                        tvFeedbackTalkSentence.text,
                        TextToSpeech.QUEUE_FLUSH,
                        params,
                        "feedback_tts"
                    )
                    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String) {}
                        override fun onDone(utteranceId: String) {
                            if (utteranceId == "feedback_tts") {
                                requireActivity().runOnUiThread {
                                    btnFeedbackTalkListen.isSelected = false
                                }
                            }
                        }

                        override fun onError(utteranceId: String) {
                            requireActivity().runOnUiThread {
                                Timber.d("피드백 TTS 오류 발생")
                            }
                        }
                    })
                } else {
                    tts?.stop()
                }
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

    private fun navigateToFeedbackLoadingFragment() = findNavController().navigate(
        R.id.action_talk_to_feedback_loading, bundleOf(
            FEEDBACK_QUESTION_EN to nextQuestionEn,
            FEEDBACK_QUESTION_KO to nextQuestionKo,
            FEEDBACK_BEFORE to binding.includeLayoutTalkSpeech.etTalkUserSpeech.text.toString()
        )
    )

    // 어댑터 연결
    private fun initTalkAdapter() {
        binding.layoutBottomSheetTalk.rvBottomSheet.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TalkAdapter().apply {
                submitList(talkMockData.toMutableList())
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
                        binding.layoutBottomSheetTalk.btnBottomSheetSelectArea.visibility = GONE
                    }
                    // 바텀 시트가 완전히 펼쳐졌을 때 버튼 보이게
                    STATE_EXPANDED -> {
                        binding.layoutBottomSheetTalk.btnBottomSheetSelectArea.visibility = VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    // 비디오 적용
    private fun setRandomVideo() {
        val videoResource = R.raw.bg_talk_ai
        val videoUri =
            Uri.parse("android.resource://${requireContext().packageName}/$videoResource")

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
                tvTalkEnglish.isVisible = !tvTalkEnglish.isVisible
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
                requestAudioPermission() // 권한 요청
                if (ContextCompat.checkSelfPermission(
                        requireContext(), Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startSpeechRecognition()
                    btnTalkSpeak.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.main)
                    lottiTalkSpeak.isVisible = true
                }
            }
        }
    }

    // 권한 요청 후 처리
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startSpeechRecognition() // 권한 승인 후 음성 인식 바로 실행
        } else {
            toast(getString(R.string.error_talk_permission))
        }
    }

    // 권한 요청
    private fun requestAudioPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        }
    }

    // 음성 인식
    private fun startSpeechRecognition() {
        if (this::speechRecognizer.isInitialized) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            }
            speechRecognizer.startListening(intent)
        } else {
            toast(getString(R.string.error_talk_permission))
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
        const val RECORD_AUDIO_PERMISSION_CODE = 100
        const val TALK_DIALOG = "talkDialog"
        const val MAX_GUIDE_CLICK = 2

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