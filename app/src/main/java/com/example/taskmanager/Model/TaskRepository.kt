package com.example.taskmanager.Model

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import kotlinx.coroutines.flow.first

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.allTasks()

   /* fun getTasksLiveData(id: Int):Flow<Task> {
        return taskDao.getTasks(id)
    }*/

    @WorkerThread
    suspend fun insertTask(task: Task)
    {
        val insertedRowID = taskDao.insertTask(task)
        Log.d("TaskInsertion", "Task inserted with ID: $insertedRowID")
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

    /*@WorkerThread
    suspend fun checkIfTaskExists(date: String) : Boolean {
        val taskCount = taskDao.getTaskCount(date).first()
        return taskCount > 0
    }*/
}