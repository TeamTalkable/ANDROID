package com.talkable.presentation.feedback

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Base64
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.speech.v1.RecognitionAudio
import com.google.cloud.speech.v1.RecognitionConfig
import com.google.cloud.speech.v1.RecognizeRequest
import com.google.cloud.speech.v1.SpeechClient
import com.google.cloud.speech.v1.SpeechRecognitionAlternative
import com.google.cloud.speech.v1.SpeechRecognitionResult
import com.google.cloud.speech.v1.SpeechSettings
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.core.util.fragment.toast
import com.talkable.core.util.fragment.viewLifeCycle
import com.talkable.core.util.fragment.viewLifeCycleScope
import com.talkable.core.view.visible
import com.talkable.databinding.FragmentFeedbackPronunciationBinding
import com.talkable.presentation.talk.VoiceRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.Locale

class FeedbackPronunciationFragment :
    BindingFragment<FragmentFeedbackPronunciationBinding>(R.layout.fragment_feedback_pronunciation),
    TextToSpeech.OnInitListener {

    private val viewModel: FeedbackViewModel by activityViewModels()
    private var tts: TextToSpeech? = null
    private var voiceRecorder: VoiceRecorder? = null
    private var byteArray: ByteArray = byteArrayOf()
    private var base64AudioData: String = ""
    private lateinit var speechClient: SpeechClient
    private lateinit var speechRecognizer: SpeechRecognizer
    private var transcription = ""

    private val voiceCallBack: VoiceRecorder.Callback = object : VoiceRecorder.Callback() {
        override fun onVoiceStart() {}

        override fun onVoice(data: ByteArray?, size: Int) {
            byteArray = data?.let { byteArray.plus(it) }!!
        }

        override fun onVoiceEnd() {
            initializeSpeechClient()
            if (::speechClient.isInitialized && byteArray.isNotEmpty()) {
                transcribeRecording(byteArray)
            }
        }
    }

    private fun initializeSpeechClient() {
        try {
            val credentials =
                GoogleCredentials.fromStream(resources.openRawResource(R.raw.google_cloud_speech))
            val settings = SpeechSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build()

            speechClient = SpeechClient.create(settings)
        } catch (e: Exception) {
            Timber.e("SpeechClient 초기화 실패: ${e.message}")
        }
    }

    override fun initView() {
        tts = TextToSpeech(requireContext(), this)
        statusBarColorOf(R.color.white)
        collect()
        initRecordCancelClickListener()
        initRecordCheckClickListener()
        initAiVoiceClickListener()
        initUserVoiceClickListener()
        initMickClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }
        stopVoiceRecorder()
        releaseSpeechClient()
    }

    private fun releaseSpeechClient() {
        if (this::speechClient.isInitialized) {
            transcribeRecording(byteArray)
        }
    }

    private fun collect() {
        viewLifeCycleScope.launch {
            viewModel.uiState.flowWithLifecycle(viewLifeCycle).collect { uiState ->
                Timber.e(uiState.toString())
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

    private fun initAiVoiceClickListener() = with(binding.ivFeedbackPronunciationAi) {
        handleTTSEndState()
        setOnClickListener {
            isSelected = !isSelected
            if (isSelected) handleTTSStartState()
        }
    }

    private fun handleTTSStartState() {
        // TTS 시작
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "tts2")
        }
        tts?.speak(
            binding.tvFeedbackPronunciationEnglish.text.toString(),
            TextToSpeech.QUEUE_FLUSH,
            params,
            "tts2"
        )
    }

    private fun handleTTSEndState() {
        // TTS 종료
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {}
            override fun onDone(utteranceId: String) {
                binding.ivFeedbackPronunciationAi.isSelected = false
            }

            override fun onError(utteranceId: String) {
                requireActivity().runOnUiThread {
                    Timber.d("TTS 오류 발생")
                }
            }
        })
    }

    private fun initUserVoiceClickListener() = with(binding.ivFeedbackPronunciationUser) {
        byteArray = viewModel.byteArray
        setOnClickListener {
            isSelected = !isSelected
            if (isSelected) playByteArrayAudio(byteArray)
        }
    }

    private fun playByteArrayAudio(audioData: ByteArray, sampleRate: Int = 16000) {
        // AudioTrack 설정
        val audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,             // 오디오 스트림 타입
            sampleRate,                            // 샘플 속도 (예: 16000Hz)
            AudioFormat.CHANNEL_OUT_MONO,          // 채널 구성 (모노)
            AudioFormat.ENCODING_PCM_16BIT,        // 오디오 데이터 인코딩 형식 (PCM 16비트)
            audioData.size,                        // 버퍼 크기 (ByteArray의 크기)
            AudioTrack.MODE_STATIC                 // STATIC 모드로 전체 데이터를 한 번에 재생
        )

        // AudioTrack에 데이터 쓰기
        audioTrack.write(audioData, 0, audioData.size)

        // 재생 시작
        audioTrack.play()

        // 재생 완료 후 해제
        audioTrack.setNotificationMarkerPosition(audioData.size / 2)  // 오디오 데이터 크기 절반 위치에 마커 설정
        audioTrack.setPlaybackPositionUpdateListener(object :
            AudioTrack.OnPlaybackPositionUpdateListener {
            override fun onMarkerReached(track: AudioTrack?) {
                track?.stop()
                track?.release()
                binding.ivFeedbackPronunciationUser.isSelected = false
            }

            override fun onPeriodicNotification(track: AudioTrack?) {}
        })
    }

    private fun initMickClickListener() = with(binding.ivFeedbackPronunciationMick) {
        setOnClickListener {
            binding.layoutFeedbackPronunciationMick.visible(true)
            startVoiceRecorder()
        }
    }

    private fun initRecordCancelClickListener() {
        binding.ivFeedbackPronunciatoinTrash.setOnClickListener {
            binding.layoutFeedbackPronunciationMick.visible(false)
            stopVoiceRecorder()
        }
    }

    private fun initRecordCheckClickListener() {
        binding.ivFeedbackPronunciationCheck.setOnClickListener {
            stopVoiceRecorder()
            if (transcription.isNotEmpty()) {
                viewModel.patchPronunciationEvaluation(transcription, base64AudioData)
                FeedbackPronunciationCompleteDialog().show(
                    childFragmentManager,
                    PRONUNCIATION_DIALOG
                )
                binding.layoutFeedbackPronunciationMick.visible(false)
            } else toast("녹음을 다시 해주세요")
        }
    }

    private fun startVoiceRecorder() {
        byteArray = byteArrayOf()
        if (voiceRecorder != null) {
            voiceRecorder!!.stop()
        }
        voiceRecorder = VoiceRecorder(requireContext(), voiceCallBack)
        voiceRecorder!!.start()
    }

    private fun stopVoiceRecorder() {
        voiceRecorder?.let {
            it.stop()
            voiceRecorder = null
        }
    }

    private fun transcribeRecording(data: ByteArray) {
        viewLifeCycleScope.launch(Dispatchers.IO) {
            try {
                base64AudioData = Base64.encodeToString(data, Base64.NO_WRAP)
                val inputStream = data.inputStream()
                if (inputStream != null) {
                    val response = speechClient.recognize(createRecognizeRequestFromVoice(data))
                    val results = response.resultsList
                    processTranscriptionResults(results)
                } else {
                    Timber.e("InputStream is null")
                }

            } catch (e: Exception) {
                Timber.e("transcribeRecording 중 오류 발생: ${e.message}")
            }
        }
    }

    private fun processTranscriptionResults(results: List<SpeechRecognitionResult>) {
        val stringBuilder = StringBuilder()
        for (result in results) {
            val recData: SpeechRecognitionAlternative = result.alternativesList[0]
            stringBuilder.append(recData.transcript)
        }
        transcription = stringBuilder.toString()
    }

    private fun createRecognizeRequestFromVoice(audioData: ByteArray): RecognizeRequest {
        val inputStream = audioData.inputStream()
        if (inputStream != null) {
            val audioBytes = RecognitionAudio.newBuilder()
                .setContent(com.google.protobuf.ByteString.copyFrom(audioData))
                .build()

            val config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setSampleRateHertz(16000)
                .setLanguageCode("en-US")
                .build()

            return RecognizeRequest.newBuilder()
                .setConfig(config)
                .setAudio(audioBytes)
                .build()
        } else {
            throw IOException("InputStream is null, 음성 데이터를 처리할 수 없습니다.")
        }
    }

    companion object {
        const val PRONUNCIATION_DIALOG = "pronunciationDialog"
        fun newInstance() = FeedbackPronunciationFragment()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        } else {
            Timber.d("TTS 초기화 실패")
        }
    }
}