package com.revoio.taskmanagementapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.revoio.taskmanagementapp.data.AuthState
import com.revoio.taskmanagementapp.vm.AuthVM

@Composable
fun Home(modifier: Modifier = Modifier, navController: NavController, authVM: AuthVM) {

    val authState = authVM.authState.observeAsState()

    LaunchedEffect(key1 = authState.value) {
        when (authState.value){
            is AuthState.Unauthenticated -> {
                navController.navigate("login")
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home")
        Button(onClick = {
            authVM.signOutUser()
        }) {
            Text(text = "Sign-Out")
        }
        Button(onClick = {
            navController.navigate("create_task")
        }) {
            Text(text = "Create Task")
        }

    }
}