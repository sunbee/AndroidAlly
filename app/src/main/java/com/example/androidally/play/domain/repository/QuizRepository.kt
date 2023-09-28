package com.example.androidally.play.domain.repository

import com.example.androidally.play.domain.model.ChapterInfo
import com.example.androidally.play.domain.model.Quiz
import com.example.androidally.util.Resource
import kotlinx.coroutines.flow.Flow

/*
* getModuleNames() - Obtain list of quiz modules
* getModuleContents() - Obtain the quizzes in the named module
*
* */
interface QuizRepository {
    suspend fun getModuleNames(
        fetchFromRemote: Boolean
    ): Resource<List<ChapterInfo>>

    suspend fun getModuleContents(
        name: String
    ): Flow<Resource<List<Quiz>>>
}