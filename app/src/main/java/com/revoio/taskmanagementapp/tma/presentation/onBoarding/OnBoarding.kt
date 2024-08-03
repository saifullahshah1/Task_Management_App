package com.revoio.taskmanagementapp.tma.presentation.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.revoio.taskmanagementapp.R
import com.revoio.taskmanagementapp.tma.presentation.theme.Black
import com.revoio.taskmanagementapp.tma.presentation.theme.DarkBlue
import com.revoio.taskmanagementapp.tma.presentation.theme.PurpleGrey
import com.revoio.taskmanagementapp.tma.presentation.theme.White
import com.revoio.taskmanagementapp.vm.AuthVM

@Composable
fun OnBoarding(modifier: Modifier = Modifier, navController: NavController, authVM: AuthVM) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_onboarding),
                contentDescription = "On Boarding Main Image",
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Tasks To Do !!",
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(25.dp, 0.dp, 25.dp, 0.dp),
                textAlign = TextAlign.Center,
                text = "Ride the wave of productivity by organizing , prioritizing and achieving",
                color = PurpleGrey,
                fontSize = 14.sp,
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 30.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = DarkBlue,
                disabledContainerColor = DarkBlue
            ),
            onClick = {
                navController.navigate(
                    "login",
                    navOptions = NavOptions.Builder().setPopUpTo("onboarding", true).build()
                )
            }) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
                    fontSize = 16.sp,
                    text = "Let's Start",
                    color = White
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_white),
                    contentDescription = "Arrow Next"
                )
            }
        }
    }
}