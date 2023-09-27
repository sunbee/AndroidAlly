package com.example.androidally.play.presentation.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidally.play.domain.model.ChapterInfo
import com.example.androidally.play.domain.repository.QuizRepository
import com.example.androidally.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val quizRepository: QuizRepository
): ViewModel() {

    val state = MutableStateFlow(LandingState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = quizRepository.getModuleNames(fetchFromRemote = true)
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        state.value = state.value.copy(
                            chapters = result.data,
                            isLoading = false
                        )
                    }
                }
                is Resource.Failure -> {
                    state.value = state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    state.value = state.value.copy(
                        isLoading = result.isLoading
                    )
                }
            }  // end WHEN
        }  // end COROUTINE
    }  // end INIT
}