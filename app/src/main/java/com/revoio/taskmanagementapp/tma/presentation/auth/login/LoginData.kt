package com.revoio.taskmanagementapp.tma.presentation.auth.login

data class LoginData(
    val emailVal : String,
    val emailError : String,
    val emailErrorMessageTxt : String,
    val isEmailInputError : Boolean,

    val passwordVal : String,
    val passwordErrorMessageTxt : String,
    val isPasswordInputError : Boolean,

    val showPassword : Boolean,
    val showForgetPasswordDialog : Boolean,
)