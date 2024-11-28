package com.talkable.presentation.review.model

import android.content.Context
import com.talkable.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class MemorizationStatus {
    DIFFICULT, MEMORIZED, MEMORIZING;

    fun getStatusText(context: Context): String {
        return when (this) {
            DIFFICULT -> context.getString(R.string.tv_saved_difficult)
            MEMORIZED -> context.getString(R.string.tv_saved_memorized)
            MEMORIZING -> context.getString(R.string.tv_saved_memorizing)
        }
    }
}

data class TalkSavedModel(
    val talkSavedId: Int = 0,
    val savedWordList: MutableList<Saved.Word> = mutableListOf(),
    val savedSentenceList: MutableList<Saved.Sentence> = mutableListOf()
)

@Serializable
sealed class Saved {
    @SerialName("status")
    abstract val status: MemorizationStatus

    @Serializable
    @SerialName("Word")
    data class Word(
        @SerialName("status")
        override val status: MemorizationStatus = MemorizationStatus.MEMORIZING,
        @SerialName("wordEnglish")
        val wordEnglish: String = "",
        @SerialName("wordKorean")
        val wordKorean: String = "",
        @SerialName("partOfSpeech")
        val partOfSpeech: String = "other"
    ) : Saved()

    @Serializable
    @SerialName("Sentence")
    data class Sentence(
        @SerialName("status")
        override val status: MemorizationStatus = MemorizationStatus.MEMORIZING,
        @SerialName("sentenceEnglish")
        val sentenceEnglish: String = "",
        @SerialName("sentenceKorean")
        val sentenceKorean: String = "",
    ) : Saved()
}