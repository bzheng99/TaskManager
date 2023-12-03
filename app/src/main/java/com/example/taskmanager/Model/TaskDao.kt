package com.example.taskmanager.Model

import androidx.annotation.WorkerThread
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_item_table WHERE id= :id")
    fun getTasks(id: Int):Flow<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task) : Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    /*@Query("SELECT * FROM task_item_table WHERE date = :date")
    suspend fun getTasksByDate(date: LocalDate): List<Task>*/
}