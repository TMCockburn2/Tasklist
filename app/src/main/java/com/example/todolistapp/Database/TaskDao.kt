package com.example.todolistapp.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todolistapp.Model.Task


@Dao
interface TaskDao {
    @Query("SELECT * FROM Task ORDER BY id DESC")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE isDeleted = true ORDER BY id DESC ")
    fun getDeletedTasks(): LiveData<List<Task>>

    @Insert
    fun addTask(task: Task)

    @Query("Delete FROM Task where id = :id")
    fun deleteTask(id: Int)

    @Query("Update Task set isChecked = :isChecked where id = :id")
    fun updateCheck(id: Int, isChecked: Boolean)

    @Query("Update Task set isDeleted = true where id = :id")
    fun removeTask(id: Int)

    @Query("Update Task set isDeleted = false where id = :id")
    fun restoreTask(id: Int)
}