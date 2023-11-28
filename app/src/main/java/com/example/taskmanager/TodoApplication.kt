package com.example.taskmanager

import android.app.Application
import com.example.taskmanager.Model.TaskDatabase
import com.example.taskmanager.Model.TaskRepository

class TodoApplication : Application()
{
    private val database by lazy { TaskDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}