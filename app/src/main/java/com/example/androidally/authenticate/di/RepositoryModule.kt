package com.example.androidally.authenticate.di

import com.example.androidally.authenticate.remote.FireStoreUserCRUD
import com.example.androidally.authenticate.remote.FireStoreUserCRUDImpl
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
    abstract fun bindFireStoreUserCRUD(
        fireStoreUserCRUDImpl: FireStoreUserCRUDImpl
    ) : FireStoreUserCRUD
}