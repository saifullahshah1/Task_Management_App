package com.revoio.taskmanagementapp.tma.presentation.auth.signup

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class SignupData (
    val usernameVal : String,
    val emailVal : String,
    val passwordVal : String,
    val emailErrorMessageTxt : String,
    val isEmailInputError : Boolean,
    val passwordErrorMessageTxt : String,
    val isPasswordInputError : Boolean,
    val showPassword : Boolean,
)