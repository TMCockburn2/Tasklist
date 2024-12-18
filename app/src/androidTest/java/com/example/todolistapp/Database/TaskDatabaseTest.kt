package com.example.todolistapp.Database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todolistapp.Model.Task
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest: TestCase(){


    private lateinit var db: TaskDatabase
    private lateinit var  dao: TaskDao

    @Before
    override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).build()
        dao = db.taskDao()

    }

    @Test
    fun testDeleteData() = runBlocking{
            val task = Task(0,"test","desc")
            val task2 = Task(1, "test2", "Desc")
            dao.addTask(task)
            dao.deleteTask(task.id)

            dao.addTask(task2)
            dao.removeTask(task2.id)


    }


    @After
    fun closeDb(){
        db.close()
    }

}