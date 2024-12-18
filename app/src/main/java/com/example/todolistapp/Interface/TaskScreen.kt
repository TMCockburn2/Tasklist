package com.example.todolistapp.Interface

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.Model.Task
import com.example.todolistapp.R
import com.example.todolistapp.VM.TaskViewModel

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    //loads all available tasks from the viewmodel
    val taskList by viewModel.taskList.observeAsState()

    //keeps track of textfields values
    var taskText by remember { mutableStateOf("") }
    var descText by remember { mutableStateOf("") }

    //assists in changing the focus between the textfields
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val spacing = 7.dp
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = taskText,

                onValueChange = {
                    taskText = it
                },
                label = {
                    Text("Enter a new task")
                },
                //keyboard option to show the next button rather than the return button. The focusManager with then move to the next textfield
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )

            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = descText,
                onValueChange = {
                    descText = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text("Enter the task description")
                },
                //closes the keyboard on completion
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (taskText == "" && descText == "") {
                        Toast.makeText(
                            context,
                            "Please add some info in order to add a task",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        viewModel.addTask(taskText, descText)
                        taskText = ""
                        descText = ""
                    }
                }
            ) {
                Text(text = "Add")
            }
        }
        //populates the cells based on tasklist value
        if (!taskList.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
                content = {
                    //will not be null from the previous condition
                    itemsIndexed(taskList!!) { index: Int, task: Task ->
                        //hides deleted items from the db without actually removing them, that way they are accessible to restore
                        if (!task.isDeleted) {
                            Row(
                                modifier = Modifier
                                    .then(
                                        if (task.isChecked) {
                                            Modifier.background(Color.Gray)
                                        } else {
                                            Modifier
                                        }
                                    )
                                    .fillMaxWidth()
                                    .padding(spacing),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Checkbox(
                                        checked = task.isChecked,
                                        onCheckedChange = {
                                            // checked = it
                                            viewModel.checkTask(task.id, !task.isChecked)
                                        },
                                    )
                                    IconButton(
                                        onClick = {
                                            viewModel.removeTask(task.id)
                                            //viewModel.deleteTask(task.id)
                                        }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_delete_24),
                                            contentDescription = "Delete"
                                        )
                                    }
                                }
                                Column {
                                    TaskItem(task = task)
                                }
                            }
                        }


                    }
                }
            )
        }
    }
}


@Composable
fun TaskItem(task: Task) {

    Text(
        text = task.title,
        fontSize = 18.sp,
    )
    Text(
        text = task.desc,
        fontSize = 14.sp,
    )

}


