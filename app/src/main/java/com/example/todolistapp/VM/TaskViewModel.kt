package com.example.todolistapp.VM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.Database.TaskDatabase
import com.example.todolistapp.Model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(database: TaskDatabase): ViewModel() {


    private val taskDao = database.taskDao()
    //live data for tasks and deleted tasks
    val taskList : LiveData<List<Task>> = taskDao.getTasks()
    val deletedTaskList: LiveData<List<Task>> = taskDao.getDeletedTasks()




    fun addTask(title: String, desc: String){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.addTask(Task(title = title, desc = desc))
        }

    }

   fun deleteTask(id: Int){
       viewModelScope.launch(Dispatchers.IO) {
           taskDao.deleteTask(id)
       }
   }

    //changes the flag for the checkbox. Initially I had the value set opposite of what the current value is so it's always reversed, but that lead to issues in the cases where the screen didn't match the db
    fun checkTask(id:Int, isChecked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateCheck(id, isChecked)
        }
    }

    fun removeTask(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.removeTask(id)
        }
    }

    fun restoreTask(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.restoreTask(id)
        }
    }
}