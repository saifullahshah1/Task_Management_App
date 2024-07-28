package com.revoio.taskmanagementapp.tma.presentation.auth.login.ui.dialogs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.revoio.taskmanagementapp.data.ResetPasswordState
import com.revoio.taskmanagementapp.tma.presentation.auth.login.vm.ForgetPasswordDialogVM
import com.revoio.taskmanagementapp.tma.presentation.auth.login.vm.LoginVM
import com.revoio.taskmanagementapp.tma.presentation.theme.Black
import com.revoio.taskmanagementapp.tma.presentation.theme.DarkBlue
import com.revoio.taskmanagementapp.tma.presentation.theme.PurpleGrey
import com.revoio.taskmanagementapp.tma.presentation.theme.Red
import com.revoio.taskmanagementapp.tma.presentation.theme.White
import com.revoio.taskmanagementapp.vm.AuthVM

@Composable
fun ForgetPasswordDialog(loginVM : LoginVM, authVM: AuthVM) {

    val TAG = "ForgetPasswordDialog"

    val forgetPasswordDialogVM : ForgetPasswordDialogVM = viewModel()

    val loginState by loginVM.loginState.collectAsState()
    val forgetPasswordDialogState by forgetPasswordDialogVM.forgetPasswordDialogState.collectAsState()

    val resetPasswordState = authVM.resetPasswordState.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(resetPasswordState.value) {
        when (resetPasswordState.value) {
            is ResetPasswordState.RequestSent -> {
                if (forgetPasswordDialogState.isEmailInputError) forgetPasswordDialogVM.setIsEmailInputError(false)
                loginVM.setShowForgetPasswordDialog(false)
            }

            is ResetPasswordState.Error -> {
                (resetPasswordState.value as ResetPasswordState.Error).message.let { resMsg ->
                    Log.d(TAG, "ForgetPasswordDialog: $resMsg")
                    when (resMsg) {
                        "The email address is badly formatted." -> {
                            forgetPasswordDialogVM.setEmailErrorMessageTxt("Enter valid email")
                            forgetPasswordDialogVM.setIsEmailInputError(true)
                        }

                        else -> {}
                    }
                }

                Toast.makeText(
                    context,
                    (resetPasswordState.value as ResetPasswordState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()

            }

            else -> Unit
        }
    }

    if (loginState.showForgetPasswordDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            onDismissRequest = {
                loginVM.setShowForgetPasswordDialog(false)
            },
            shape = MaterialTheme.shapes.large,
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Forgot Password?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                    )
                }
            },
            text = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Enter your email for verification process, we will send a link to reset password. ",
                        fontSize = 14.sp,
                        color = PurpleGrey,
                        style = TextStyle(textAlign = TextAlign.Center)
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Email",
                            color = Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (forgetPasswordDialogState.isEmailInputError) Red else PurpleGrey,
                            unfocusedBorderColor = if (forgetPasswordDialogState.isEmailInputError) Red else PurpleGrey,
                            errorSupportingTextColor = Red,
                            focusedTextColor = if (forgetPasswordDialogState.isEmailInputError) Red else Black,
                            unfocusedTextColor = if (forgetPasswordDialogState.isEmailInputError) Red else Black,
                        ),
                        shape = RoundedCornerShape(percent = 10),
                        value = forgetPasswordDialogState.emailVal,
                        onValueChange = {
                            forgetPasswordDialogVM.setEmailVal(it)
                            if (forgetPasswordDialogState.isEmailInputError) forgetPasswordDialogVM.setIsEmailInputError(false)
                        },
                        label = {
                            Text(
                                "Enter your email",
                                color = if (forgetPasswordDialogState.isEmailInputError) Red else PurpleGrey
                            )
                        },
                        singleLine = true,
                        supportingText = {
                            if (forgetPasswordDialogState.isEmailInputError) {
                                Text(
                                    text = forgetPasswordDialogState.emailErrorMessageTxt,
                                    color = Red,
                                )
                            }
                        },
                        isError = forgetPasswordDialogState.isEmailInputError,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .border(
                                    2.dp,
                                    DarkBlue,
                                    RoundedCornerShape(5.dp),
                                ),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                disabledContainerColor = White,
                            ),
                            onClick = {
                                loginVM.setShowForgetPasswordDialog(false)
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
                                fontSize = 16.sp,
                                text = "Cancel",
                                color = DarkBlue
                            )
                        }
                        Button(
                            modifier = Modifier.wrapContentWidth(),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = White,
                                containerColor = DarkBlue,
                                disabledContainerColor = DarkBlue
                            ),
                            onClick = {authVM.forgotPassword(forgetPasswordDialogState.emailVal)},
                        ) {
                            Text(
                                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
                                fontSize = 16.sp,
                                text = "Done",
                                color = White
                            )
                        }
                    }
                }


            },
            confirmButton = {}
        )
    }
}