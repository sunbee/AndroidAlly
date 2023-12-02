package com.example.androidally.authenticate.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidally.authenticate.data.SignInResult
import com.example.androidally.authenticate.remote.FireStoreUserCRUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val fireStoreUserCRUD: FireStoreUserCRUD
): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(signInResult: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = signInResult.data != null,
                signInError = signInResult.errorMessage
            )
        }
        signInResult.data?.let {
            viewModelScope.launch(Dispatchers.IO) {
                fireStoreUserCRUD.addNewUser(signInResult.data)
            }
        }
    }

    fun resetSignInState() {
        _state.update {
            SignInState()
        }
    }

}