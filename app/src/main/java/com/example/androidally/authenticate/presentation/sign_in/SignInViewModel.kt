package com.example.androidally.authenticate.presentation.sign_in

import androidx.lifecycle.ViewModel
import com.example.androidally.authenticate.data.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(signInResult: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = signInResult.data != null,
                signInError = signInResult.errorMessage
            )
        }
    }

    fun resetSignInState() {
        _state.update {
            SignInState()
        }
    }

}