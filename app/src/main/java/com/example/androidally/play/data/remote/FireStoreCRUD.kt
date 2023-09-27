package com.example.androidally.play.data.remote

import com.example.androidally.play.domain.model.ChapterInfo
import com.example.androidally.play.domain.model.QuizQuestion

interface FireStoreCRUD {

    suspend fun fetchModuleNames(): List<ChapterInfo>

    suspend fun fetchModuleContents(name: String): List<QuizQuestion>
}