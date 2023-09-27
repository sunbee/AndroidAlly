package com.example.androidally.play.domain.model

/*
* A quiz question has components as follows:
* 1. number: A unique rank representing order in a sequence
* 2. question: The question
* 3. answer: The expected answer
* 4. image: A supporting image URL
* 5. youTubeId: The id of a YouTube video
* 6. start: The time in seconds marking start of video for content related to the question
*
* We will display the record in the UI screen either as knowledge or a quiz.
* */
data class QuizQuestion(
    val questionNumber: Int,
    val question: String,
    val answer: String,
    val image: String?,
    val youTubeVideoId: String,
    val timestamp: Int,
    val chapterKey: String,
    val isActive: Boolean
)
