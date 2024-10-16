package com.talkable.presentation.talk

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.SpannableStringBuilder
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.cloud.speech.v1.SpeechClient
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.Key.FEEDBACK_BEFORE
import com.talkable.core.util.Key.FEEDBACK_QUESTION_EN
import com.talkable.core.util.Key.FEEDBACK_QUESTION_KO
import com.talkable.core.util.context.pxToDp
import com.talkable.core.util.fragment.stringOf
import com.talkable.core.util.fragment.toast
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentTalkBinding
import com.talkable.presentation.FeedbackTextColor
import com.talkable.presentation.feedback.FeedbackUiState
import com.talkable.presentation.feedback.FeedbackViewModel
import com.talkable.presentation.feedback.model.FeedbackContainer
import com.talkable.presentation.firstTalk
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

class TalkFragment : BindingFragment<FragmentTalkBinding>(R.layout.fragment_talk),
    TextToSpeech.OnInitListener {

    private val viewModel: FeedbackViewModel by activityViewModels()

    private var clickCount = FIRST_CLICK
    private lateinit var speechRecognizer: SpeechRecognizer
    private var tts: TextToSpeech? = null
    private lateinit var nextQuestionEn: String
    private lateinit var nextQuestionKo: String

    private var guideClickCount = FIRST_CLICK
    private var textIndex = 0
    private val englishGuideTextArray =
        arrayOf(R.string.tv_talk_second_en, R.string.tv_talk_third_en)
    private val koreanGuideTextArray =
        arrayOf(R.string.tv_talk_second_kr, R.string.tv_talk_third_kr)

    private var voiceRecorder: VoiceRecorder? = null
    private var byteArray: ByteArray = byteArrayOf()
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nextQuestionEn = stringOf(R.string.tv_talk_english)
        nextQuestionKo = stringOf(R.string.tv_talk_korean)
    }


    override fun initView() {
        blockNavigateToBack()
        collect()
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
        tts = TextToSpeech(requireContext(), this) // TTS 초기화
    }

    private fun blockNavigateToBack() =
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {}

    //data collect
    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                when (uiState) {
                    is FeedbackUiState.PatchGptFeedbacks -> {
                        binding.groupTalkFeedbackLoading.visible(false)
                        binding.groupTalkFeedback.visible(true)
                        binding.tvTalkFeedbackDetail.visible(true)
                        binding.groupTalkAi.visible(false)
                        setNextQuestionText(uiState.data)
                        setFeedbackLayout(
                            uiState.data.afterFullAnswer,
                            uiState.data.afterAnswerParts
                        )
                    }

                    is FeedbackUiState.PatchPronunciationFeedbacks -> {
                        binding.groupTalkAi.visible(true)
                        binding.groupTalkFeedback.visible(false)
                        //임시 데이터
                        binding.tvTalkEnglish.text =
                            "You did very well! Your pronunciation accuracy just now was 70%."
                        binding.tvTalkTranslate.text = "아주 잘했어! 방금 너의 발음 정확도는 70%였어."
                        delay(3000)
                        initNextQuestionLayout()
                        viewModel.setEmptyState()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun setNextQuestionText(data: FeedbackContainer) {
        nextQuestionEn = data.nextQuestionEn
        nextQuestionKo = data.nextQuestionKo
    }

    private fun initNextQuestionLayout() = with(binding) {
        tvTalkEnglish.text = nextQuestionEn
        tvTalkTranslate.text = nextQuestionKo
        setBtnTalkSpeakVisibility(isVisible = true)
        groupTalkBtn.visible(true)
        tvTalkFeedbackDetail.visible(false)
        groupTalkAi.visible(false)
        tvTalkListen.visible(true)
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
                        groupTalkSpeak.isVisible = true
                        btnTalkSpeak.isEnabled = true
                        tvTalkEnglish.isVisible = false
                        tvTalkTranslate.isVisible = false
                        tvTalkListen.isVisible = true
                        setQuestionLayout()
                        binding.root.setOnClickListener { }
                    }
                }
            }
        }
    }

    private fun setQuestionLayout() = with(binding) {
        tvTalkEnglish.text = nextQuestionEn
        tvTalkTranslate.text = nextQuestionKo
        groupTalkBtn.isVisible = true
    }

    private val voiceCallBack: VoiceRecorder.Callback = object : VoiceRecorder.Callback() {
        override fun onVoiceStart() {}

        override fun onVoice(data: ByteArray?, size: Int) {
            byteArray = data?.let { byteArray.plus(it) }!!
        }

        override fun onVoiceEnd() {}
    }

    // TTS 초기화 완료 시 호출
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        } else {
            Timber.d("TTS 초기화 실패")
        }
    }

    // 음성 녹음 시작
    private fun startVoiceRecorder() {
        if (voiceRecorder != null) {
            voiceRecorder!!.stop()
        }
        voiceRecorder = VoiceRecorder(requireContext(), voiceCallBack)
        voiceRecorder!!.start()
    }

    // 음성 녹음 종료
    private fun stopVoiceRecorder() {
        voiceRecorder?.let {
            it.stop()
            voiceRecorder = null
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

                // TTS 종료
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String) {}
                    override fun onDone(utteranceId: String) {
                        if (utteranceId == "tts1") {
                            requireActivity().runOnUiThread {
                                videoViewTalkBackground.pause()  // 비디오 일시정지
                                videoViewTalkBackground.seekTo(1)  // 첫 프레임으로 돌아가기
                                btnTalkListen.isSelected = false  // 버튼 상태 초기화
                                initSpeakGuide(isFirstAnswer = true)
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

    override fun onDestroyView() {
        super.onDestroyView()
        tts?.apply {
            stop()
            shutdown()
        }
        tts = null

        if (this::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }
        stopVoiceRecorder()
    }

    private fun initSpeakGuide(isFirstAnswer: Boolean) {
        binding.tvTalkGuide.apply {
            text =
                if (isFirstAnswer) getString(R.string.tv_talk_guide) else getString(R.string.tv_talk_guide_again)
            visible(true)
            postDelayed({
                visible(false)
            }, 3000)
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
            viewModel.setEmptyState()
            navigateToTotalTalkFeedback()
        }
    }

    private fun navigateToTotalTalkFeedback() {
        viewModel.setEmptyState()
        findNavController().navigate(R.id.action_talk_to_talk_feedback)
    }

    private fun initSpeakCompleteBtnClickListener() {
        binding.includeLayoutTalkSpeech.ivTalkSpeech.setOnClickListener {
            viewModel.patchGptFeedbacks(binding.includeLayoutTalkSpeech.etTalkUserSpeech.text.toString())
            setVisibleFeedbackLoading(true)
        }
    }

    private fun setVisibleFeedbackLoading(isVisible: Boolean) = with(binding) {
        setBtnIconSelect()
        includeBottomSheetTalk.visible(true)
        groupTalkFeedbackLoading.visible(isVisible)
        groupTalkBtn.visible(!isVisible)
        groupTalkAi.visible(!isVisible)
        groupTalkFeedback.visible(!isVisible)
        tvTalkListen.visible(!isVisible)
        tvTalkGuide.visible(!isVisible)
        binding.includeLayoutTalkSpeech.layoutTalkSpeech.visible(!isVisible)
    }

    private fun setBtnIconSelect() = with(binding) {
        btnTalkListen.isSelected = false
        btnTalkTranslate.isSelected = false
        btnTalkShow.isSelected = false
    }

    private fun setBtnTalkSpeakVisibility(isVisible: Boolean) = with(binding) {
        btnTalkSpeak.isVisible = isVisible
        tvTalkHint.isVisible = isVisible
    }

    private fun navigateToSavedFeedback() =
        findNavController().navigate(R.id.action_talk_to_today_saved)

    private fun initTalkStackBtnClickListener() {
        binding.btnTalkFolder.setOnClickListener {
            navigateToSavedFeedback()
        }
    }

    //set feedback layout
    private fun setFeedbackLayout(fullText: String, partsText: List<String>) = with(binding) {
        tvTalkUserAnswer.text = binding.includeLayoutTalkSpeech.etTalkUserSpeech.text.toString()
        setFeedbackTextColor(fullText, partsText)
        initFeedbackDetailTvClickListener()
        initSpeakGuide(isFirstAnswer = true)
        setBtnTalkSpeakVisibility(isVisible = true)
    }

    //set feedback text parts color
    private fun setFeedbackTextColor(fullText: String, partsText: List<String>) {
        val spannableString =
            FeedbackTextColor(requireContext()).setAfterAnswerTextColor(fullText, partsText)

        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append("너가 말한 문장은 \"")
        spannableStringBuilder.append(spannableString)
        spannableStringBuilder.append("\"라고 말하는 것이\n")
        spannableStringBuilder.append("문법적으로 올바르고 더 자연스러운 문장이야.\n")
        spannableStringBuilder.append("수정된 문장으로 다시 말해볼래?")

        binding.tvTalkFeedback.text = spannableStringBuilder
    }

    private fun initFeedbackDetailTvClickListener() {
        binding.tvTalkFeedbackDetail.setOnClickListener {
            when (viewModel.uiState.value) {
                is FeedbackUiState.PatchGptFeedbacks -> navigateToFeedbackFragment()
                is FeedbackUiState.PatchPronunciationFeedbacks -> navigateToFeedbackPronunciationFragment()
                else -> Unit
            }
        }
    }

    private fun navigateToFeedbackFragment() = findNavController().navigate(
        R.id.action_talk_to_feedback_expression, bundleOf(
            FEEDBACK_QUESTION_EN to nextQuestionEn,
            FEEDBACK_QUESTION_KO to nextQuestionKo,
            FEEDBACK_BEFORE to binding.includeLayoutTalkSpeech.etTalkUserSpeech.text.toString()
        )
    )

    private fun navigateToFeedbackPronunciationFragment() =
        findNavController().navigate(R.id.action_talk_to_feedback_pronunciation)

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
                    if (isRecording) {
                        stopVoiceRecorder()
                        setSpeakBtnState(isSpeaking = false)
                        isRecording = false
                    } else {
                        startVoiceRecorder()
                        setSpeakBtnState(isSpeaking = true)
                        isRecording = true
                    }
                }
            }
        }
    }

    private fun setSpeakBtnState(isSpeaking: Boolean) = with(binding) {
        lottiTalkSpeak.visible(isSpeaking)
        binding.btnTalkSpeak.backgroundTintList =
            if (isSpeaking) ContextCompat.getColorStateList(
                requireContext(),
                R.color.main
            ) else null
    }

    // 권한 요청 후 처리
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecorder() // 권한 승인 후 음성 인식 바로 실행
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