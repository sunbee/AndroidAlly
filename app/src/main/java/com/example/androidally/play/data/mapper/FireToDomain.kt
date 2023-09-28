package com.example.androidally.play.data.mapper

import com.example.androidally.play.data.remote.ChapterInfoFire
import com.example.androidally.play.data.remote.QuizQuestionFire
import com.example.androidally.play.domain.model.ChapterInfo
import com.example.androidally.play.domain.model.Quiz

fun ChapterInfoFire.toChapterInfo(): ChapterInfo {
    return ChapterInfo(
        name = name,
        title = title,
        description = description
    )
}

fun QuizQuestionFire.toQuizQuestion(): Quiz {
    return Quiz(
        questionNumber = number,
        question = question,
        answer = answer,
        chapterKey = chapterName,
        youTubeVideoId = youTubeId,
        timestamp = start,
        image = image,
        isActive = isActive
    )
}