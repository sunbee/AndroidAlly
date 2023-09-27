package com.example.androidally.play.data.repository

import android.util.Log
import com.example.androidally.play.data.remote.FireStoreCRUD
import com.example.androidally.play.domain.model.ChapterInfo
import com.example.androidally.play.domain.model.QuizQuestion
import com.example.androidally.play.domain.repository.QuizRepository
import com.example.androidally.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val fireStoreCRUD: FireStoreCRUD
): QuizRepository {
    override suspend fun getModuleNames(fetchFromRemote: Boolean): Resource<List<ChapterInfo>> {
        return try {
            val modules = fireStoreCRUD.fetchModuleNames()
            Resource.Success(data = modules)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(message = e.message ?: "Got no chapter info. from Firestore!")
        }
    }

    override suspend fun getModuleContents(name: String): Flow<Resource<List<QuizQuestion>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val knowledgeBank = fireStoreCRUD.fetchModuleContents(name)
                emit(Resource.Success(data = knowledgeBank))
            } catch (e: Exception) {
                emit(Resource.Failure(message = e.message ?: "Got not contents for ${name} in Firestore!"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }
}