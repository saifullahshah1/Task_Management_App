package com.revoio.taskmanagementapp.tma.presentation.auth.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignupVM : ViewModel() {

    val signupState = MutableStateFlow(
        SignupData(
            usernameVal = "",
            emailVal = "",
            passwordVal = "",
            emailErrorMessageTxt = "",
            isEmailInputError = false,
            passwordErrorMessageTxt = "",
            isPasswordInputError = false,
            showPassword = true,
        )
    )

    fun setUsernameVal(usernameVal: String){
        signupState.update {
            signupState.value.copy(usernameVal = usernameVal)
        }
    }
    fun setEmailVal(emailVal: String){
        signupState.update {
            signupState.value.copy(emailVal = emailVal)
        }
    }
    fun setEmailErrorMessageTxt(emailErrorMessageTxt : String)  {
        signupState.update {
            signupState.value.copy(emailErrorMessageTxt = emailErrorMessageTxt)
        }
    }
    fun setIsEmailInputError(isEmailInputError: Boolean)  {
        signupState.update {
            signupState.value.copy(isEmailInputError = isEmailInputError)
        }
    }
    fun setPasswordVal(passwordVal : String) {
        signupState.update {
            signupState.value.copy(passwordVal = passwordVal)
        }
    }
    fun setPasswordErrorMessageTxt(passwordErrorMessageTxt : String) {
        signupState.update {
            signupState.value.copy(passwordErrorMessageTxt = passwordErrorMessageTxt)
        }
    }
    fun setIsPasswordInputError(isPasswordInputError : Boolean) {
        signupState.update {
            signupState.value.copy(isPasswordInputError = isPasswordInputError)
        }
    }
    fun setShowPassword(showPassword : Boolean) {
        signupState.update {
            signupState.value.copy(showPassword = showPassword)
        }
    }

}