package com.revoio.taskmanagementapp.tma.presentation.auth.login.ui

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.GoogleAuthProvider
import com.revoio.taskmanagementapp.tma.core.Constants.SERVER_CLIENT
import com.revoio.taskmanagementapp.R
import com.revoio.taskmanagementapp.data.AuthState
import com.revoio.taskmanagementapp.tma.core.getActivity
import com.revoio.taskmanagementapp.tma.presentation.auth.login.ui.dialogs.ForgetPasswordDialog
import com.revoio.taskmanagementapp.tma.presentation.auth.login.vm.LoginVM
import com.revoio.taskmanagementapp.tma.presentation.theme.*
import com.revoio.taskmanagementapp.vm.AuthVM

@Composable
fun Login(modifier: Modifier = Modifier, navController: NavController, authVM: AuthVM) {

    val TAG = "Login"

    val loginVM: LoginVM = viewModel()
    val loginState by loginVM.loginState.collectAsState()

    val authState = authVM.authState.observeAsState()

    val context = LocalContext.current

    if (loginState.showForgetPasswordDialog) {
        ForgetPasswordDialog(loginVM = loginVM, authVM = authVM)
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                if (loginState.isEmailInputError) loginVM.setIsEmailInputError(false)
                if (loginState.isPasswordInputError) loginVM.setIsPasswordInputError(false)
                navController.navigate("home")
            }

            is AuthState.Error -> {

                (authState.value as AuthState.Error).message.let { resMsg ->
                    Log.d(TAG, "Login: $resMsg")

                    when (resMsg) {
                        "The supplied auth credential is incorrect, malformed or has expired." -> {
                            loginVM.setEmailErrorMessageTxt("Email not valid")
                            loginVM.setPasswordErrorMessageTxt("Incorrect Password")
                            loginVM.setIsEmailInputError(true)
                            loginVM.setIsPasswordInputError(true)
                        }

                        "The email address is badly formatted." -> {
                            loginVM.setEmailErrorMessageTxt("Email not valid")
                            loginVM.setIsEmailInputError(true)
                        }

                        else -> {}
                    }
                }

                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { launcherResult ->
            val account = GoogleSignIn.getSignedInAccountFromIntent(launcherResult.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                authVM.loginUsingGoogle(credentials)
            } catch (it: ApiException) {
                print(it)
            }
        })

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            /*verticalArrangement = Arrangement.Center,*/
            /*horizontalAlignment = Alignment.CenterHorizontally*/
        ) {
            Spacer(modifier = Modifier.height(78.dp))
            Text(
                text = "Login",
                color = Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Welcome back \uD83D\uDC4B , Please log in into your account",
                color = PurpleGrey,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(39.dp))
            Text(
                text = "Email",
                color = Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(7.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (loginState.isEmailInputError) Red else PurpleGrey,
                    unfocusedBorderColor = if (loginState.isEmailInputError) Red else PurpleGrey,
                    errorSupportingTextColor = Red,
                    focusedTextColor = if (loginState.isEmailInputError) Red else Black,
                    unfocusedTextColor = if (loginState.isEmailInputError) Red else Black,
                ),
                shape = RoundedCornerShape(percent = 10),
                value = loginState.emailVal,
                onValueChange = {
                    loginVM.setEmail(it)
                    if (loginState.isEmailInputError) loginVM.setIsEmailInputError(false)
                },
                label = {
                    Text(
                        "Enter your email",
                        color = if (loginState.isEmailInputError) Red else PurpleGrey
                    )
                },
                supportingText = {
                    if (loginState.isEmailInputError) {
                        Text(
                            text = loginState.emailErrorMessageTxt,
                            color = Red,
                        )
                    }
                },
                isError = loginState.isEmailInputError,
            )
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "Password",
                color = Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(7.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (loginState.isPasswordInputError) Red else AquaGrey,
                    unfocusedBorderColor = if (loginState.isPasswordInputError) Red else AquaGrey,
                    errorSupportingTextColor = Red,
                    focusedTextColor = if (loginState.isPasswordInputError) Red else Black,
                    unfocusedTextColor = if (loginState.isPasswordInputError) Red else Black,
                ),
                shape = RoundedCornerShape(percent = 10),
                value = loginState.passwordVal,
                onValueChange = {
                    loginVM.setPasswordVal(it)
                    if (loginState.isPasswordInputError) loginVM.setIsPasswordInputError(false)
                },
                label = {
                    Text(
                        "Enter your password",
                        color = if (loginState.isPasswordInputError) Red else PurpleGrey
                    )
                },
                singleLine = true,
                supportingText = {
                    if (loginState.isPasswordInputError) {
                        Text(
                            text = loginState.passwordErrorMessageTxt,
                            color = Red,
                        )
                    }
                },
                isError = loginState.isPasswordInputError,
                visualTransformation = if (loginState.showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (loginState.showPassword) {
                        IconButton(onClick = { loginVM.setShowPassword(false) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_show),
                                contentDescription = "show_password",
                                tint = Color.Unspecified
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { loginVM.setShowPassword(true) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_not_show),
                                contentDescription = "hide_password",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.clickable {
                        loginVM.setShowForgetPasswordDialog(true)
                    },
                    text = "Forgot Password?",
                    fontSize = 15.sp,
                    color = Red,
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = DarkBlue,
                        disabledContainerColor = DarkBlue
                    ),
                    onClick = {
                        authVM.loginUser(loginState.emailVal, loginState.passwordVal)
                    }) {
                    Text(
                        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
                        fontSize = 16.sp,
                        text = "Login",
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(17.dp))
                Text(
                    text = "OR",
                    color = PurpleGrey,
                    fontWeight = FontWeight.Bold

                )
                Spacer(modifier = Modifier.height(17.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            2.dp,
                            DarkBlue,
                            RoundedCornerShape(5.dp),
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        disabledContainerColor = White
                    ),
                    onClick = {
                        authVM.loginUsingGoogle(context) { googleSignInClient ->
                            launcher.launch(googleSignInClient.signInIntent)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(ButtonDefaults.IconSize * 2),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
                        fontSize = 16.sp,
                        text = "Login with Google",
                        color = DarkBlue
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(

                ) {
                    Text(
                        text = "Don’t have an account? ",
                        color = PurpleGrey
                    )
                    Text(
                        text = "Signup",
                        color = Red,
                        modifier = Modifier.clickable {
                            navController.navigate("signup")
                        }
                    )
                }
            }
        }
    }
}