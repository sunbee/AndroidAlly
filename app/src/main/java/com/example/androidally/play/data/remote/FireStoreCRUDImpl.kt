package com.example.androidally.play.data.remote

import android.util.Log
import com.example.androidally.play.data.mapper.toChapterInfo
import com.example.androidally.play.data.mapper.toQuizQuestion
import com.example.androidally.play.domain.model.ChapterInfo
import com.example.androidally.play.domain.model.QuizQuestion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreCRUDImpl @Inject constructor(): FireStoreCRUD {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun fetchModuleNames(): List<ChapterInfo> {
        var moduleNames = mutableListOf<ChapterInfo>()
        try {
            val querySnapshot = firestore.collection("AndroidAllyContents").get().await()
            for (document in querySnapshot.documents) {
                var chapterInfoFire = document.toObject<ChapterInfoFire>()
                chapterInfoFire = chapterInfoFire?.copy(
                    name = document.id
                )
                chapterInfoFire?.let {
                    moduleNames.add(it.toChapterInfo())
                }
                Log.d("FIRE_CRUD", "Got chapter id ${chapterInfoFire?.name} title ${chapterInfoFire?.title}")
            }  // end FOR
        } catch (e: Exception) {
            e.printStackTrace()
        }  // end TRY-CATCH
        return moduleNames
    }  // end FUN

    override suspend fun fetchModuleContents(name: String): List<QuizQuestion> {
        val quizzes = mutableListOf<QuizQuestion>()
        try {
            val querySnapshot = firestore.collection(name).get().await()
            for (document in querySnapshot.documents) {
                var quizQuestionFire = document.toObject<QuizQuestionFire>()
                quizQuestionFire = quizQuestionFire?.copy(
                    chapterName = name
                )
                quizQuestionFire?.let {
                    quizzes.add(it.toQuizQuestion())
                }
                Log.d("FIRE_CRUD", "Got quiz question with number ${quizQuestionFire?.number}")
            }  // end FOR
        } catch (e: Exception) {
            e.printStackTrace()
        }  // end TRY-CATCH
        return quizzes
    }
}
