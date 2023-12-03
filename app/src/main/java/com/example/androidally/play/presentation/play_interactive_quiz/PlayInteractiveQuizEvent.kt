package com.example.androidally.play.presentation.play_interactive_quiz

sealed class PlayInteractiveQuizEvent {
    object OnClickedShowAnswer: PlayInteractiveQuizEvent()
    object OnClickedCorrect: PlayInteractiveQuizEvent()
    object OnClickedPartial: PlayInteractiveQuizEvent()
    object OnClickedIncorrect: PlayInteractiveQuizEvent()
}
