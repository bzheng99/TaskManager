package com.example.taskmanager

import com.example.taskmanager.Model.Task

interface TaskClickListener
{

    fun editTask(task: Task)
    fun completeTask(task: Task)
}