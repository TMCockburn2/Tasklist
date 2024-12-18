package com.example.todolistapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var desc: String,
    var isChecked: Boolean = false,
    var isDeleted: Boolean = false
)
