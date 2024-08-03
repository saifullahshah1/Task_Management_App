package com.revoio.taskmanagementapp.tma.presentation.auth.signup

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.revoio.taskmanagementapp.R
import com.revoio.taskmanagementapp.data.AuthState
import com.revoio.taskmanagementapp.tma.core.debug
import com.revoio.taskmanagementapp.tma.presentation.theme.AquaGrey
import com.revoio.taskmanagementapp.tma.presentation.theme.Black
import com.revoio.taskmanagementapp.tma.presentation.theme.DarkBlue
import com.revoio.taskmanagementapp.tma.presentation.theme.PurpleGrey
import com.revoio.taskmanagementapp.tma.presentation.theme.Red
import com.revoio.taskmanagementapp.tma.presentation.theme.White
import com.revoio.taskmanagementapp.vm.AuthVM

@Composable
fun Signup(modifier: Modifier = Modifier, navController: NavController, authVM: AuthVM) {

    val TAG = "Signup"

    val signupVM : SignupVM = viewModel()
    val signupState by signupVM.signupState.collectAsState()

    val authState by authVM.authState.observeAsState()
    var errorMessage by remember{
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(authState) {
        "LaunchedEffect:".debug(TAG)
        when (authState) {
            is AuthState.Authenticated -> {
                if(signupState.isEmailInputError) signupVM.setIsEmailInputError(false)
                if(signupState.isPasswordInputError) signupVM.setIsPasswordInputError(false)
                navController.navigate("home")
            }

            is AuthState.Error -> {
                (authState as? AuthState.Error)?.message.let { resMsg ->
                    Log.d(TAG, "Signup: $resMsg")
                    when (resMsg) {
                        "The email address is already in use by another account." -> {
                            signupVM.setEmailErrorMessageTxt("Email already exists. Try logging in instead.")
                            signupVM.setIsEmailInputError(true)
                        }

                        "The given password is invalid. [ Password should be at least 6 characters ]" -> {
                            signupVM.setPasswordErrorMessageTxt("Password too weak.  At least 6 letters or numbers")
                            signupVM.setIsPasswordInputError(true)
                        }

                        "The email address is badly formatted." -> {
                            signupVM.setEmailErrorMessageTxt("Email not valid")
                            signupVM.setIsEmailInputError(true)

                        }

                        else -> {}
                    }

                    Toast.makeText(
                        context,
                        resMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> Unit
        }
    }

    DisposableEffect(key1 = errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        onDispose { errorMessage = "" }
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(78.dp))
            Text(
                text = "Signup",
                color = Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Welcome to Tasks To Do \uD83D\uDC4B , Enter your details and stay on track with ease..",
                color = PurpleGrey,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = "Username",
                color = Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PurpleGrey,
                    unfocusedBorderColor = PurpleGrey,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                ),
                shape = RoundedCornerShape(percent = 10),
                value = signupState.usernameVal,
                onValueChange = {
                    signupVM.setUsernameVal(it)
                },
                label = {
                    Text(
                        "Enter your username",
                        color = PurpleGrey
                    )
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Email",
                color = Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (signupState.isEmailInputError) Red else PurpleGrey,
                    unfocusedBorderColor = if (signupState.isEmailInputError) Red else PurpleGrey,
                    errorSupportingTextColor = Red,
                    focusedTextColor = if (signupState.isEmailInputError) Red else Black,
                    unfocusedTextColor = if (signupState.isEmailInputError) Red else Black,
                ),
                shape = RoundedCornerShape(percent = 10),
                value = signupState.emailVal,
                onValueChange = {
                    signupVM.setEmailVal(it)
                    if(signupState.isEmailInputError) signupVM.setIsEmailInputError(false)
                },
                label = {
                    Text(
                        "Enter your email",
                        color = if (signupState.isEmailInputError) Red else PurpleGrey
                    )
                },
                singleLine = true,
                supportingText = {
                    if(signupState.isEmailInputError){
                        Text(
                            text = signupState.emailErrorMessageTxt,
                            color = Red,
                        )
                    }
                },
                isError = signupState.isEmailInputError,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Password",
                color = Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (signupState.isPasswordInputError) Red else AquaGrey,
                    unfocusedBorderColor = if (signupState.isPasswordInputError) Red else AquaGrey,
                    errorSupportingTextColor = Red,
                    focusedTextColor = if (signupState.isPasswordInputError) Red else Black,
                    unfocusedTextColor = if (signupState.isPasswordInputError) Red else Black,
                ),
                shape = RoundedCornerShape(percent = 10),
                value = signupState.passwordVal,
                onValueChange = {
                    signupVM.setPasswordVal(it)
                    if(signupState.isPasswordInputError) signupVM.setIsPasswordInputError(false)
                },
                label = {
                    Text(
                        "Enter your password",
                        color = if (signupState.isPasswordInputError) Red else PurpleGrey
                    )
                },
                singleLine = true,
                supportingText = {
                    if(signupState.isPasswordInputError){
                        Text(
                            text = signupState.passwordErrorMessageTxt,
                            color = Red,
                        )
                    }
                },
                isError = signupState.isPasswordInputError,
                visualTransformation = if (signupState.showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (signupState.showPassword) {
                        IconButton(onClick = { signupVM.setShowPassword(false) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_show),
                                contentDescription = "show_password",
                                tint = Color.Unspecified
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { signupVM.setShowPassword(true) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_not_show),
                                contentDescription = "hide_password",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            )
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
                        authVM.signUpUser(signupState.usernameVal, signupState.emailVal, signupState.passwordVal)
                    }) {
                    Text(
                        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
                        fontSize = 16.sp,
                        text = "Signup",
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
                        text = "Signup with Google",
                        color = DarkBlue
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(

                ) {
                    Text(
                        text = "Already have an account? ",
                        color = PurpleGrey
                    )
                    Text(
                        text = "Login",
                        color = Red,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}