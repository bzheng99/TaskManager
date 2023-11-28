package com.example.taskmanager.AddTaskActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.Model.Task
import com.example.taskmanager.NewEditTaskActivity.TaskViewHolder
import com.example.taskmanager.TaskClickListener
import com.example.taskmanager.databinding.TaskItemCellBinding

class TaskAdapter(
    private val tasks: List<Task>,
    private val clickListener: TaskClickListener
): RecyclerView.Adapter<TaskViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskViewHolder(parent.context, binding, clickListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}