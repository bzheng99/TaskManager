package com.example.taskmanager.Model

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import kotlinx.coroutines.flow.first

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.allTasks()

   /* fun getTasksLiveData(id: Int):Flow<Task> {
        return taskDao.getTasks(id)
    }*/

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTask(task: Task)
    {
        val insertedRowID = taskDao.insertTask(task)
        Log.d("TaskInsertion", "Task inserted with ID: $insertedRowID")
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTask(task: Task)
    {
        taskDao.updateTask(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteTask(task: Task)
    {
        taskDao.deleteTask(task)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun checkIfDateHasTask(selectedDate: String): Boolean {
        val taskCount = taskDao.countTasksForDate(selectedDate)
        return taskCount > 0
    }

}