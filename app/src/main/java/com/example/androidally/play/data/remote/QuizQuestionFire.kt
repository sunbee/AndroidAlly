package com.example.androidally.play.data.remote

import com.google.firebase.firestore.PropertyName

data class QuizQuestionFire(
    val chapterName: String = "",

    @PropertyName("Number")
    val number: Int = 1,

    @PropertyName("Question")
    val question: String = "",

    @PropertyName("Answer")
    val answer: String = "",

    @PropertyName("Image")
    val image: String? = null,

    @PropertyName("YouTubeId")
    val youTubeId: String = "",

    @PropertyName("Start")
    val start: Int = 0,

    @PropertyName("IsActive")
    val isActive: Boolean = true
)
