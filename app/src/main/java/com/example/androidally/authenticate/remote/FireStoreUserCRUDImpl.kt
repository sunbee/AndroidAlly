package com.example.androidally.authenticate.remote

import com.example.androidally.authenticate.data.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreUserCRUDImpl @Inject constructor(): FireStoreUserCRUD {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun addNewUser(user: UserData) {
        /*
        * user: UserData from SignInResult
        * */
        try {
            val querySnapshot = firestore.collection("User")
                .whereEqualTo("UserId", user.userId)
                .get()
                .await()
            if (querySnapshot.isEmpty) {
                val newUser = UserInfoFire(
                    userid = user.userId,
                    username = user.username,
                    profilePic = user.profilePictureURL
                )
                firestore.collection("User").document(user.userId).set(newUser).await()
            } else {
                println("Added NO user! Already exists!")
            }
        } catch (e: Exception) {
            println("Added NO user! Error!")
        }
    }
}