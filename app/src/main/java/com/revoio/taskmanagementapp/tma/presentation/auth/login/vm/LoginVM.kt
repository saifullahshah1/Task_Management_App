package com.revoio.taskmanagementapp.tma.presentation.auth.login.vm

import androidx.lifecycle.ViewModel
import com.revoio.taskmanagementapp.tma.presentation.auth.login.LoginData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginVM : ViewModel() {

    val loginState = MutableStateFlow(
        LoginData(
            emailVal = "",
            emailError = "",
            emailErrorMessageTxt = "",
            isEmailInputError = false,
            passwordVal = "",
            passwordErrorMessageTxt = "",
            isPasswordInputError = false,
            showPassword = false,
            showForgetPasswordDialog = false
        )
    )

    fun setEmail(emailVal: String){
        loginState.update {
            loginState.value.copy(emailVal = emailVal)
        }
    }
    fun setEmailError(emailError : String) {
        loginState.update {
            loginState.value.copy(emailError = emailError)
        }
    }
    fun setEmailErrorMessageTxt(emailErrorMessageTxt : String)  {
        loginState.update {
            loginState.value.copy(emailErrorMessageTxt = emailErrorMessageTxt)
        }
    }
    fun setIsEmailInputError(isEmailInputError: Boolean)  {
        loginState.update {
            loginState.value.copy(isEmailInputError = isEmailInputError)
        }
    }
    fun setPasswordVal(passwordVal : String) {
        loginState.update {
            loginState.value.copy(passwordVal = passwordVal)
        }
    }
    fun setPasswordErrorMessageTxt(passwordErrorMessageTxt : String) {
        loginState.update {
            loginState.value.copy(passwordErrorMessageTxt = passwordErrorMessageTxt)
        }
    }
    fun setIsPasswordInputError(isPasswordInputError : Boolean) {
        loginState.update {
            loginState.value.copy(isPasswordInputError = isPasswordInputError)
        }
    }
    fun setShowPassword(showPassword : Boolean) {
        loginState.update {
            loginState.value.copy(showPassword = showPassword)
        }
    }
    fun setShowForgetPasswordDialog(showForgetPasswordDialog : Boolean) {
        loginState.update {
            loginState.value.copy(showForgetPasswordDialog = showForgetPasswordDialog)
        }
    }


}