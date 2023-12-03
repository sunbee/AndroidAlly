package com.example.androidally.play.presentation.play_interactive_quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidally.play.domain.repository.QuizRepository
import com.example.androidally.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayInteractiveQuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = MutableStateFlow(PlayInteractiveQuizState())
    val module = savedStateHandle.get<String>("name") ?: ""

    init {
        getBank(name=module)
    }

    private fun getBank(
        fetchRemote: Boolean = false,
        name: String = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = state.value.copy(
                isLoading = true
            )
            quizRepository
                .getModuleContents(name)
                .collect() { result ->
                    when(result) {
                        is Resource.Success -> {
                            state.value = state.value.copy(
                                bank = result.data.orEmpty()
                            )
                        }
                        is Resource.Failure -> {
                            state.value = state.value.copy(
                                error = result.message
                            )
                        }
                        is Resource.Loading -> {
                            state.value = state.value.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }  // end WHEN
                }  // end COLLECT
        }  // end COROUTINE
    }  // end FUN

    fun advanceIndex() {
        var presentIndex = state.value.currentIndex
        if (presentIndex < state.value.bank.size - 1) {
            presentIndex += 1
        } else {
            presentIndex = 0
        }
        state.value = state.value.copy(
            currentIndex = presentIndex,
            showQuestionOnly = true
        )
    }

    fun displayAnswer() {
        state.value = state.value.copy(
            showQuestionOnly = false
        )
    }

    fun onEvent(event: PlayInteractiveQuizEvent) {
        when (event) {
            is PlayInteractiveQuizEvent.OnClickedCorrect -> {
                advanceIndex()
            }
            is PlayInteractiveQuizEvent.OnClickedPartial -> {
                advanceIndex()
            }
            is PlayInteractiveQuizEvent.OnClickedIncorrect -> {
                advanceIndex()
            }
            is PlayInteractiveQuizEvent.OnClickedShowAnswer -> {
                displayAnswer()
            }
            else -> Unit
        }

    }
}