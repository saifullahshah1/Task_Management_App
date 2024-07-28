package com.revoio.taskmanagementapp.data

sealed class ResetPasswordState {
    object RequestSent : ResetPasswordState()
    object RequestNotSent : ResetPasswordState()
    object Loading : ResetPasswordState()
    data class Error(val message: String) : ResetPasswordState()
}