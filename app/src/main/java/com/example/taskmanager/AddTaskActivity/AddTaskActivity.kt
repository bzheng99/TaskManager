package com.example.taskmanager.AddTaskActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.NewEditTaskActivity.NewTaskSheet
import com.example.taskmanager.Model.Task
import com.example.taskmanager.Model.TaskModelFactory
import com.example.taskmanager.Model.TaskViewModel
import com.example.taskmanager.TaskClickListener
import com.example.taskmanager.TodoApplication
//import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.databinding.TaskActivityMainBinding


class AddTaskActivity : AppCompatActivity(), TaskClickListener
{
    private lateinit var binding: TaskActivityMainBinding
    //private lateinit var taskViewModel: TaskViewModel
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = TaskActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }

    private fun setRecyclerView()
    {
        val mainActivity = this
        taskViewModel.tasks.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskAdapter(it, mainActivity)
            }
        }
    }

    override fun editTask(task: Task)
    {
        NewTaskSheet(task).show(supportFragmentManager,"newTaskTag")
    }

    override fun completeTask(task: Task)
    {
        taskViewModel.setCompleted(task)
    }

}
