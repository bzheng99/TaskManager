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
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((application as TodoApplication).repository)
    }

    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = TaskActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve selected date from intent extras
        val selectedDate = intent.getStringExtra("selectedDate")
        if (selectedDate != null) {
            supportActionBar?.title = "Tasks for $selectedDate"
        }

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null, selectedDate.toString()).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }

    private fun setRecyclerView()
    {
        val mainActivity = this
        taskViewModel.tasks.observe(this) { allTasks ->
            // Filter tasks based on the selected date
            val tasksForSelectedDate = allTasks.filter { it.date == selectedDate.toString() }

            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskAdapter(tasksForSelectedDate, mainActivity)
            }
        }
    }

    override fun editTask(task: Task)
    {
        val newTaskSheet = NewTaskSheet(task, selectedDate.toString())
        newTaskSheet.show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTask(task: Task)
    {
        taskViewModel.setCompleted(task)
    }

}
