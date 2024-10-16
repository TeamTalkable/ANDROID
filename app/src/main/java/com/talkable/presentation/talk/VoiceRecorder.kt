package com.talkable.presentation.talk

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat

class VoiceRecorder(private val context: Context, private val callback: Callback) {

    abstract class Callback {
        open fun onVoiceStart() {}
        open fun onVoice(data: ByteArray?, size: Int) {}
        open fun onVoiceEnd() {}
    }

    private var audioRecord: AudioRecord? = null
    private var thread: Thread? = null
    private var buffer: ByteArray? = null
    private val lock = Any()

    private var lastVoiceHeardMillis = Long.MAX_VALUE
    private var voiceStartedMillis: Long = 0

    fun start() {
        stop()
        audioRecord = createAudioRecord()?.apply {
            startRecording()
            thread = Thread(ProcessVoice()).apply { start() }
        } ?: throw RuntimeException("Cannot instantiate VoiceRecorder")
    }

    fun stop() {
        synchronized(lock) {
            dismiss()
            thread?.interrupt()
            thread = null
            audioRecord?.apply {
                stop()
                release()
            }
            audioRecord = null
            buffer = null
        }
    }

    fun dismiss() {
        if (lastVoiceHeardMillis != Long.MAX_VALUE) {
            lastVoiceHeardMillis = Long.MAX_VALUE
            callback.onVoiceEnd()
        }
    }

    private fun createAudioRecord(): AudioRecord? {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return null
        }

        return SAMPLE_RATE_CANDIDATES.asSequence()
            .mapNotNull { sampleRate ->
                val bufferSize = AudioRecord.getMinBufferSize(sampleRate, CHANNEL, ENCODING)
                if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                    AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, CHANNEL, ENCODING, bufferSize).takeIf {
                        it.state == AudioRecord.STATE_INITIALIZED
                    }
                } else null
            }.firstOrNull().also {
                buffer = it?.let { ByteArray(it.bufferSizeInFrames) }
            }
    }

    private inner class ProcessVoice : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                synchronized(lock) {
                    val size = audioRecord?.read(buffer!!, 0, buffer!!.size) ?: return
                    processVoice(size)
                }
            }
        }

        private fun processVoice(size: Int) {
            val now = System.currentTimeMillis()
            if (isHearingVoice(buffer, size)) {
                if (lastVoiceHeardMillis == Long.MAX_VALUE) {
                    voiceStartedMillis = now
                    callback.onVoiceStart()
                }
                callback.onVoice(buffer, size)
                lastVoiceHeardMillis = now
                if (now - voiceStartedMillis > MAX_SPEECH_LENGTH_MILLIS) end()
            } else if (lastVoiceHeardMillis != Long.MAX_VALUE && now - lastVoiceHeardMillis > SPEECH_TIMEOUT_MILLIS) {
                callback.onVoice(buffer, size)
                end()
            }
        }

        private fun end() {
            lastVoiceHeardMillis = Long.MAX_VALUE
            callback.onVoiceEnd()
        }

        private fun isHearingVoice(buffer: ByteArray?, size: Int): Boolean {
            for (i in 0 until size step 2) {
                val amplitude = (buffer!![i + 1].toInt().and(0xFF) shl 8) + buffer[i].toInt().and(0xFF)
                if (amplitude > AMPLITUDE_THRESHOLD) return true
            }
            return false
        }
    }

    companion object {
        private val SAMPLE_RATE_CANDIDATES = intArrayOf(16000, 11025, 22050, 44100)
        private const val CHANNEL = AudioFormat.CHANNEL_IN_MONO
        private const val ENCODING = AudioFormat.ENCODING_PCM_16BIT
        private const val AMPLITUDE_THRESHOLD = 1500
        private const val SPEECH_TIMEOUT_MILLIS = 2000
        private const val MAX_SPEECH_LENGTH_MILLIS = 30 * 1000
    }
}