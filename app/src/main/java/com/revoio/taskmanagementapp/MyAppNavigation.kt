package com.revoio.taskmanagementapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revoio.taskmanagementapp.tma.presentation.auth.login.ui.Login
import com.revoio.taskmanagementapp.tma.presentation.auth.signup.Signup
import com.revoio.taskmanagementapp.ui.screens.Home
import com.revoio.taskmanagementapp.ui.screens.OnBoarding
import com.revoio.taskmanagementapp.vm.AuthVM

@Composable
fun MyAppNavigation(navController:NavHostController,modifier: Modifier = Modifier, authVM : AuthVM) {

    NavHost(navController = navController, startDestination = "onboarding", builder = {
        composable("onboarding") {
            OnBoarding(modifier,navController,authVM)
        }
        composable("login") {
            Login(modifier,navController,authVM)
        }
        composable("signup") {
            Signup(modifier,navController,authVM)
        }
        composable("home") {
            Home(modifier,navController,authVM)
        }
    })
}