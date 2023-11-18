package com.example.taskmanager.CalendarActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
// import androidx.compose.runtime.Composable
/*import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource*/
import android.widget.CalendarView
import com.example.taskmanager.R
import java.time.DayOfWeek
import java.time.YearMonth

// private val pageBackGroundColor: Color @Composable get() =

class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarFragment: CalendarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            as CalendarFragment? ?:CalendarFragment()
        /*if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CalendarFragment())
                .commit()
        }

        calendarFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            as CalendarFragment? ?:CalendarFragment()*/
    }
}