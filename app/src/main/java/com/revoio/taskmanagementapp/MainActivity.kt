package com.revoio.taskmanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.revoio.taskmanagementapp.tma.presentation.theme.TaskManagementAppTheme
import com.revoio.taskmanagementapp.vm.AuthVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authVM: AuthVM by viewModels()

        setContent {
            TaskManagementAppTheme(
                darkTheme = false,
            ) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize(),
                ) { innerPadding ->
                    MyAppNavigation(
                        navController,
                        modifier = Modifier.padding(innerPadding), authVM = authVM
                    )
                }
            }
        }
    }
}