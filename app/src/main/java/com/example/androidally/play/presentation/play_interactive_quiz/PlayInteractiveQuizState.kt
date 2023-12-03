package com.example.androidally.play.presentation.play_interactive_quiz

import com.example.androidally.play.domain.model.Quiz

data class PlayInteractiveQuizState(
    val bank: List<Quiz> = emptyList(),
    val currentIndex: Int = 0,
    val showQuestionOnly: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)
