package com.example.taskmanager.Model

import androidx.lifecycle.*
import com.example.taskmanager.Model.Task
import com.example.taskmanager.Model.TaskRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskRepository): ViewModel()
{
    val tasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

    fun addTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun setCompleted(task: Task) = viewModelScope.launch {
        if (!task.isCompleted())
            task.completedDateString = Task.dateFormatter.format(LocalDate.now())
        repository.updateTask(task)
    }
}

class TaskModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}