package com.example.androidally.play.data.remote

import com.google.firebase.firestore.PropertyName

data class QuizQuestionFire(
    val chapterName: String = "",

    @get:PropertyName("Number")
    val number: Int,

    @get:PropertyName("Question")
    val question: String,

    @get:PropertyName("Answer")
    val answer: String,

    @get:PropertyName("Image")
    val image: String?,

    @get:PropertyName("YouTubeId")
    val youTubeId: String,

    @get:PropertyName("Start")
    val start: Int,

    @get:PropertyName("IsActive")
    val isActive: Boolean
)
