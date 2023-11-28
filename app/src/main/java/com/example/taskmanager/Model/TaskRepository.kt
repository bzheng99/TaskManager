package com.example.taskmanager.Model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.allTasks()

    @WorkerThread
    suspend fun insertTask(task: Task)
    {
        taskDao.insertTask(task)
    }

    @WorkerThread
    suspend fun updateTask(task: Task)
    {
        taskDao.updateTask(task)
    }

    @WorkerThread
    suspend fun deleteTask(task: Task)
    {
        taskDao.deleteTask(task)
    }
}