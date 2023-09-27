package com.example.androidally.play.data.di

import com.example.androidally.play.data.remote.FireStoreCRUD
import com.example.androidally.play.data.remote.FireStoreCRUDImpl
import com.example.androidally.play.data.repository.QuizRepositoryImpl
import com.example.androidally.play.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFireStoreCRUD(
        fireStoreCRUDImpl: FireStoreCRUDImpl
    ): FireStoreCRUD

    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        quizRepositoryImpl: QuizRepositoryImpl
    ): QuizRepository

}