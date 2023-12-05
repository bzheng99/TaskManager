package com.example.taskmanager.Model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task) : Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_item_table WHERE date = :selectedDate")
    fun getTasksForDate(selectedDate: String): Flow<List<Task>>

    // Add a method to check if there are tasks for a specific date
    @Query("SELECT COUNT(*) FROM task_item_table WHERE date = :selectedDate")
    suspend fun countTasksForDate(selectedDate: String): Int
}