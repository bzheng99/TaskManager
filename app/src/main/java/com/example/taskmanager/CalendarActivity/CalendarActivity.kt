package com.example.taskmanager.CalendarActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.R
import com.example.taskmanager.TodoApplication


class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarFragment: CalendarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            as CalendarFragment? ?:CalendarFragment()

    }
}