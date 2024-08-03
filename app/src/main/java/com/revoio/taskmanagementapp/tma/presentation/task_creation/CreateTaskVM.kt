package com.revoio.taskmanagementapp.tma.presentation.task_creation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CreateTaskVM : ViewModel() {

    val createTaskState = MutableStateFlow(
        CreateTaskData(
            taskTitle = "",
        )
    )

    fun setTaskTitle(taskTitle: String) {
        createTaskState.update {
            createTaskState.value.copy(taskTitle = taskTitle)
        }
    }

}