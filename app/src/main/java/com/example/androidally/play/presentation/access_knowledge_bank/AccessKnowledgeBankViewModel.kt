package com.example.androidally.play.presentation.access_knowledge_bank

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidally.play.domain.repository.QuizRepository
import com.example.androidally.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessKnowledgeBankViewModel @Inject constructor(
    val quizRepository: QuizRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel() {

    val state = MutableStateFlow(AccessKnowledgeBankState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("FIRE CRUD", "${savedStateHandle.get<String>("name")}")
            val module = savedStateHandle.get<String>("name") ?: return@launch
            state.value = state.value.copy(
                module = module,
                isLoading = true
            )
            quizRepository
                .getModuleContents(module)
                .collect() { result ->
                    when (result) {
                        is Resource.Success -> {
                            state.value = state.value.copy(
                                questionBank = result.data?.map {quiz ->
                                    quiz.question
                                } ?: emptyList()
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
                }  // end COLLECT RESULT
        }  // end COROUTINE
    }  // end INIT
}