package com.talkable.presentation.talk

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Base64
import java.io.ByteArrayOutputStream

@SuppressLint("MissingPermission")
object Recording {
    private var isRecording = false

    private val sampleRate = 16000 // 16kHz 샘플링 주파수
    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    private val audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT,
        bufferSize
    )

    private val audioBuffer = ByteArray(bufferSize)
    private val outputStream = ByteArrayOutputStream()

    fun startRecordingAndEncodeBase64() {
        outputStream.reset()
        audioRecord.startRecording()

        isRecording = true
        Thread {
            while (isRecording) {
                val readBytes = audioRecord.read(audioBuffer, 0, bufferSize)
                if (readBytes > 0) {
                    val encodedData = Base64.encodeToString(audioBuffer.copyOfRange(0, readBytes), Base64.NO_WRAP)
                    outputStream.write(encodedData.toByteArray())
                }
            }
        }.start()
    }

    fun stopRecording() {
        isRecording = false
        audioRecord.stop()
        audioRecord.release()
    }

    fun getRecordingByte(): String = outputStream.toString()
}