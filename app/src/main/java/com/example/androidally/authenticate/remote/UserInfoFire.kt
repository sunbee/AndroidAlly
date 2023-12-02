package com.example.androidally.authenticate.remote

import com.google.firebase.firestore.PropertyName

data class UserInfoFire(
    @PropertyName("UserId")
    val userid: String = "",

    @PropertyName("UserName")
    val username: String? = null,

    @PropertyName("ProfilePic")
    val profilePic: String? = null,

    val score: List<Score> = emptyList()
)

data class Score(
    val chapterName: String = "",

    @PropertyName("Number")
    val number: Int = 1,

    @PropertyName("Score")
    val score: String = "",

    @PropertyName("Timestamp")
    val ts: String = ""
)

