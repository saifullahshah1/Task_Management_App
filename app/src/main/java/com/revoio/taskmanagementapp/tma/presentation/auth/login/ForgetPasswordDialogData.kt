package com.revoio.taskmanagementapp.tma.presentation.auth.login

data class ForgetPasswordDialogData (
    val emailVal : String,
    val isEmailInputError : Boolean,
    val emailErrorMessageTxt : String,
)