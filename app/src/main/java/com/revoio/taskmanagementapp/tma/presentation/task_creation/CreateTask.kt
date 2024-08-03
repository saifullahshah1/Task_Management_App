package com.revoio.taskmanagementapp.tma.presentation.task_creation

import android.widget.EditText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.revoio.taskmanagementapp.R
import com.revoio.taskmanagementapp.tma.presentation.auth.login.vm.LoginVM
import com.revoio.taskmanagementapp.tma.presentation.theme.Black


@Composable
fun CreateTask(modifier: Modifier = Modifier, navController: NavController) {

    val TAG = "CreateTask"

    val createTaskVM: CreateTaskVM = viewModel()
    val createTaskState by createTaskVM.createTaskState.collectAsState()

    val context = LocalContext.current

    ConstraintLayout {

        val (
            backIcon,
            nameTitle,
            taskTitleText,
            editTitleIcon,
            timelineStartTitle,
            timelineStartText,
            timelineStartSelection,
        ) = createRefs()

        Icon(
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
                .constrainAs(backIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            painter = painterResource(R.drawable.ic_back_button_blue),
            contentDescription = "Back Button",
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.constrainAs(nameTitle) {
                    top.linkTo(backIcon.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                },
            text = "Name:",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
        )
        Text/*Field*/(
            modifier = Modifier.constrainAs(taskTitleText) {
                    top.linkTo(backIcon.bottom, margin = 16.dp)
                    start.linkTo(nameTitle.end , margin= 8.dp)
                },
            /*value = createTaskState.taskTitle,
            onValueChange = {
                createTaskVM.setTaskTitle(it)
            },
            label = {
                Text("Title", color = Black)
            },*/
            text = "Create task",
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
        Icon(
            modifier = Modifier.constrainAs(editTitleIcon){
                  top.linkTo(nameTitle.top)
                  bottom.linkTo(nameTitle.bottom)
                  start.linkTo(taskTitleText.end , margin = 8.dp)
                },
            painter = painterResource(id = R.drawable.ic_edit) ,
            contentDescription = "Edit Icon"
        )

        Text(
            modifier = Modifier.constrainAs(timelineStartTitle) {
                top.linkTo(nameTitle.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            },
            text = "From",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
        )
        Text(
            modifier = Modifier.constrainAs(timelineStartText) {
                top.linkTo(timelineStartTitle.bottom, margin = 8.dp)
                start.linkTo(timelineStartTitle.start, margin = 4.dp)
            },
            text = "Sun, Jul 14 09:56 pm",
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )


    }
}