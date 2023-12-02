package com.example.androidally.authenticate.remote

import com.example.androidally.authenticate.data.UserData

interface FireStoreUserCRUD {

    suspend fun addNewUser(user: UserData)
}