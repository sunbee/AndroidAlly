package com.example.androidally.play.presentation.landing

import com.example.androidally.play.domain.model.ChapterInfo

data class LandingState(
    val chapters: List<ChapterInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
