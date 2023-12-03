package com.example.taskmanager

import android.app.Application
import com.example.taskmanager.Model.Task
import com.example.taskmanager.Model.TaskDatabase
import com.example.taskmanager.Model.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TodoApplication : Application()
{
    val applicationScope = CoroutineScope(SupervisorJob())
    // val database by lazy { TaskDatabase.getDatabase(this)}
    val database by lazy { TaskDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}