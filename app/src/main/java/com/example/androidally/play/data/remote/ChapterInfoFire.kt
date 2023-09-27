package com.example.androidally.play.data.remote

import com.google.firebase.firestore.PropertyName

data class ChapterInfoFire(
    val name: String = "",

    @PropertyName("Title")
    val title: String = "",

    @PropertyName("Description")
    val description: String = "",
)

