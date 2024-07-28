package com.revoio.taskmanagementapp.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.revoio.taskmanagementapp.data.AuthState
import com.revoio.taskmanagementapp.data.ResetPasswordState
import com.revoio.taskmanagementapp.tma.core.Constants.SERVER_CLIENT
import com.revoio.taskmanagementapp.tma.core.getActivity

class AuthVM : ViewModel() {

    val TAG = "AuthVM"

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _resetPasswordState = MutableLiveData<ResetPasswordState>()
    val resetPasswordState: LiveData<ResetPasswordState> = _resetPasswordState

    init {
        checkAuthStatus()
    }

    /** Check whether user is authenticated or not. */
    fun checkAuthStatus() {
        if (firebaseAuth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    /** Login */
    fun loginUser(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()){
            _authState.value =
                AuthState.Error("Email or Password can't be empty!")
            return
        }

        _authState.value = AuthState.Loading
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value =
                    AuthState.Error(response.exception?.message ?: "Something went wrong!")
            }
        }
    }


    /** Signup */
    fun signUpUser(username: String, email: String, password: String) {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
            _authState.value =
                AuthState.Error("Email , Password & Username can't be empty!")
            return
        }

        _authState.value = AuthState.Loading
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value =
                    AuthState.Error(response.exception?.message ?: "Something went wrong!")
            }
        }
    }

    /** Sign-out */
    fun signOutUser(){
        firebaseAuth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    /** Authentication with Google*/
    fun loginUsingGoogle(credential : AuthCredential){
        _authState.value = AuthState.Loading
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value =
                    AuthState.Error(response.exception?.message ?: "Something went wrong!")
            }
        }
    }
    fun loginUsingGoogle(context : Context, launchSystemSheet: (GoogleSignInClient) -> Unit ){
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(SERVER_CLIENT)
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context,googleSignInOption)

        val account = GoogleSignIn.getLastSignedInAccount(context)
        if(account==null){
            launchSystemSheet(googleSignInClient)
        }else{
            // revoke access
            context.getActivity()?.let{ aty ->
                googleSignInClient.revokeAccess().addOnCompleteListener(aty, OnCompleteListener<Void?> {
                    launchSystemSheet(googleSignInClient)
                })
            }
        }
    }

    /** Forgot Password*/
    fun forgotPassword(email : String){
        if(email.isEmpty()){
            _resetPasswordState.value = ResetPasswordState.Error("Email can't be empty!")
            return
        }

        _resetPasswordState.value = ResetPasswordState.Loading
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { response ->
                if(response.isSuccessful){
                    _resetPasswordState.value = ResetPasswordState.RequestSent
                }
            else{
                    _resetPasswordState.value = ResetPasswordState.Error(response.exception?.message ?: "Something went wrong!")
            }
        }
    }


}