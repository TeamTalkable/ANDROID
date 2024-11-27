package com.talkable.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talkable.data.FirebaseFactory
import com.talkable.data.ServicePool
import com.talkable.data.dto.request.Message
import com.talkable.data.dto.request.RequestGptDto
import com.talkable.presentation.home.model.MemorizationStatus
import com.talkable.presentation.home.model.Saved
import com.talkable.presentation.home.model.TalkSavedModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import timber.log.Timber

class SavedViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SavedUiState>(SavedUiState.Empty)
    val uiState = _uiState.asStateFlow()

    var talkSavedModel = TalkSavedModel()

    private val learnedModule = SerializersModule {
        polymorphic(Saved::class) {
            subclass(Saved.Word::class, Saved.Word.serializer())
            subclass(Saved.Sentence::class, Saved.Sentence.serializer())
            default { Saved.Word.serializer() }
        }
    }

    private val json = Json {
        allowTrailingComma = true
        serializersModule = learnedModule
        classDiscriminator = "type"
    }

    // GPT 응답 요청 및 처리
    fun getSavedMeaning(text: String) {
        viewModelScope.launch {
            _uiState.value = SavedUiState.Loading

            // GPT 요청 실행
            runCatching {
                ServicePool.gptService.getGptAnswers(
                    RequestGptDto(
                        model = "gpt-3.5-turbo",
                        maxTokens = 1000,
                        messages = listOf(
                            Message("user", generateWordRequestContent(text))
                        )
                    )
                )
            }.onSuccess {
                val response = it.choices.first().message
                handleGptResponse(response.content) // 응답 처리
            }.onFailure { exception ->
                Timber.e(exception, "Failed to get GPT response")
                _uiState.value =
                    SavedUiState.Error("Failed to get GPT response: ${exception.message}")
            }
        }
    }

    // GPT 응답 처리
    private fun handleGptResponse(content: String) {
        runCatching {
            json.decodeFromString<Saved>(content)
        }.onSuccess { savedData ->
            when (savedData) {
                is Saved.Word -> processWord(savedData)
                is Saved.Sentence -> processSentence(savedData)
            }
            _uiState.value = SavedUiState.Success(talkSavedModel)
        }.onFailure { exception ->
            Timber.e(exception, "Failed to parse GPT response: $content")
            _uiState.value =
                SavedUiState.Error("Failed to parse GPT response: ${exception.message}")
        }
    }

    // Word 데이터 처리
    private fun processWord(wordData: Saved.Word) {
        val fixedWordData = wordData.copy(
            wordKorean = wordData.wordKorean,
            status = MemorizationStatus.MEMORIZING
        )
        talkSavedModel.savedWordList.add(fixedWordData)
        saveToFirebase(fixedWordData, "savedWordList")
    }

    // Sentence 데이터 처리
    private fun processSentence(sentenceData: Saved.Sentence) {
        val fixedSentenceData = sentenceData.copy(status = MemorizationStatus.MEMORIZING)
        talkSavedModel.savedSentenceList.add(fixedSentenceData)
        saveToFirebase(fixedSentenceData, "savedSentenceList")
    }

    // Firebase 저장
    private fun saveToFirebase(savedData: Saved, path: String) {
        val serializedData = json.encodeToString(Saved.serializer(), savedData)
        Timber.d("Saving to Firebase: $serializedData")

        FirebaseFactory.savedRef.child(path).push().setValue(savedData)
            .addOnSuccessListener {
                Timber.d("$path 저장 성공: $savedData")
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "$path 저장 실패: ${exception.message}")
            }
    }

    // 요청 메시지 생성
    private fun generateWordRequestContent(text: String): String {
        return """
    Please provide the meaning, translation, and part of speech of the following text:
    "$text"
    
    The response should be in the following JSON format:
    {
        "type": "Word" | "Sentence", // "Word" for a word, "Sentence" for a sentence
        "status": "MEMORIZED" | "MEMORIZING" | "DIFFICULT",
        "wordEnglish": "<English Word>",  // only for Word
        "wordKorean": "<Korean Translation>",  // only for Word
        "partOfSpeech": "noun" | "verb" | "adjective" | "other", // Part of speech of the word
        "sentenceEnglish": "<English Sentence>",  // only for Sentence
        "sentenceKorean": "<Korean Translation>"  // only for Sentence
    }

    For "Word" type responses:
    - If the part of speech is "noun", prepend "n." before the Korean translation.
    - If the part of speech is "verb", prepend "v." before the Korean translation.
    - If the part of speech is "adjective", prepend "a." before the Korean translation.
    - Otherwise, do not prepend anything.
"""
    }

    fun setEmptyState() {
        _uiState.value = SavedUiState.Empty
    }

    fun updateSavedData() {
        _uiState.value = SavedUiState.Success(talkSavedModel)
    }

    sealed interface SavedUiState {
        data object Loading : SavedUiState
        data object Empty : SavedUiState
        data class Success(val data: TalkSavedModel) : SavedUiState
        data class Error(val errorMessage: String) : SavedUiState
    }
}