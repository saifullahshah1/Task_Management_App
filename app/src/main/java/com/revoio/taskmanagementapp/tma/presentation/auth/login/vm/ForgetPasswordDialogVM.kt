package com.revoio.taskmanagementapp.tma.presentation.auth.login.vm

import androidx.lifecycle.ViewModel
import com.revoio.taskmanagementapp.tma.presentation.auth.login.ForgetPasswordDialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ForgetPasswordDialogVM : ViewModel() {

    val forgetPasswordDialogState = MutableStateFlow(
        ForgetPasswordDialogData(
            emailVal = "",
            isEmailInputError = false,
            emailErrorMessageTxt = "",
        )
    )

    fun setEmailVal(emailVal : String){
        forgetPasswordDialogState.update {
            forgetPasswordDialogState.value.copy( emailVal = emailVal)
        }
    }
    fun setIsEmailInputError(isEmailInputError : Boolean){
        forgetPasswordDialogState.update {
            forgetPasswordDialogState.value.copy( isEmailInputError = isEmailInputError)
        }
    }
    fun setEmailErrorMessageTxt(emailErrorMessageTxt : String){
        forgetPasswordDialogState.update {
            forgetPasswordDialogState.value.copy( emailErrorMessageTxt = emailErrorMessageTxt)
        }
    }
}