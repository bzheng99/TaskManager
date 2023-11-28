package com.example.taskmanager.CalendarActivity

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.core.view.children
import android.view.LayoutInflater
import android.view.View
// import android.view.ViewGroup
// import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.example.taskmanager.databinding.CalendarHeaderBinding
import com.example.taskmanager.databinding.CalendarFragmentBinding
import com.example.taskmanager.databinding.CalendarDayBinding

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import com.example.taskmanager.R
import com.example.taskmanager.displayText
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarFragment : Fragment(R.layout.calendar_fragment) {
    private var selectedDate: LocalDate? = null
    private lateinit var binding: CalendarFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CalendarFragmentBinding.bind(view)

        // Initial Configuration of Calendar
        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)
        // Calendar Setup
        configureBinders(daysOfWeek)
        binding.exFiveCalendar.setup(startMonth, endMonth, daysOfWeek.first())
        binding.exFiveCalendar.scrollToMonth(currentMonth)

        // When user scrolls to new month, listener updates the displayed month and resets the
        // selected date.
        binding.exFiveCalendar.monthScrollListener = { month ->
            binding.exFiveMonthYearText.text = month.yearMonth.displayText()

            // Clear selection if we scroll to new month
            selectedDate?.let{
                selectedDate = null
                binding.exFiveCalendar.notifyDateChanged(it)
            }
        }

        // Next Month Button
        binding.exFiveNextMonthImage.setOnClickListener {
            binding.exFiveCalendar.findFirstVisibleMonth()?.let {
                binding.exFiveCalendar.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        // Previous Month Button
        binding.exFivePreviousMonthImage.setOnClickListener {
            binding.exFiveCalendar.findFirstVisibleMonth()?.let {
                binding.exFiveCalendar.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }

    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if(day.position == DayPosition.MonthDate) {
                        if(selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            val binding = this@CalendarFragment.binding
                            binding.exFiveCalendar.notifyDateChanged(day.date)
                            oldDate?. let { binding.exFiveCalendar.notifyDateChanged(it) }
                            Log.d("Calendar Fragment", selectedDate.toString())
                            // PROBABLY THE SPOT TO CALL METHOD FOR STARTING ADD TASK METHOD
                            val fabAddTask = (activity as CalendarActivity).findViewById<FloatingActionButton>(R.id.fabAddTask)
                            fabAddTask.setOnClickListener {
                                AddTaskActivity()
                            }
                        }
                    }
                }
            }
        }
        binding.exFiveCalendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val context = container.binding.root.context
                val textView = container.binding.exFiveDayText
                val layout = container.binding.exFiveDayLayout
                textView.text = data.date.dayOfMonth.toString()

                // Configure color of the text for the dates
                // E.g. Rolling dates from the previous month need to be a different color
                if(data.position == DayPosition.MonthDate) {
                    textView.setTextColorRes(R.color.example_5_text_grey)
                    layout.setBackgroundResource(if (selectedDate == data.date) R.drawable.example_5_selected_bg else 0)
                } else {
                    textView.setTextColorRes(R.color.example_5_text_grey_light)
                    layout.background = null
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }

        val typeFace = Typeface.create("sans-serif-light", Typeface.NORMAL)
        binding.exFiveCalendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    if(container.legendLayout.tag == null) {
                        container.legendLayout.tag = data.yearMonth
                        container.legendLayout.children.map {it as TextView}
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].displayText(uppercase = true)
                                tv.typeface = typeFace
                            }
                    }
                }
            }
    }

}

