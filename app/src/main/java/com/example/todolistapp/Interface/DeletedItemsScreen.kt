package com.example.todolistapp.Interface

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.Model.Task
import com.example.todolistapp.R
import com.example.todolistapp.VM.TaskViewModel

@Composable
fun DeletedItemsScreen(viewModel: TaskViewModel) {
    //keeps track of deleted items with a flag in the db, and displays them below
    val taskList by viewModel.deletedTaskList.observeAsState()
    val context = LocalContext.current

    if (!taskList.isNullOrEmpty()) {
        LazyColumn(
            content = {
                itemsIndexed(taskList!!) { _: Int, task: Task ->
                    Row {
                        Column {
                            IconButton(
                                onClick = {
                                    //sets the flag back to false to indicated the item isnt deleted
                                    viewModel.restoreTask(task.id)
                                    Toast.makeText(context,"Deleted task restored!", Toast.LENGTH_SHORT).show()
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_undo_24),
                                    contentDescription = "Undo"
                                )
                            }
                        }
                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = task.title,
                                fontSize = 18.sp,

                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = task.desc,
                                fontSize = 14.sp,
                            )
                        }

                    }
                }

            }
        )
    }
}

