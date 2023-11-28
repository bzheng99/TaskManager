package com.example.taskmanager.NewEditTaskActivity

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.util.Calendar
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.Model.Task
import com.example.taskmanager.Model.TaskViewModel
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime
import java.time.LocalDate


class NewTaskSheet(var taskItem: Task?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var dueDate: LocalDate? = null

    lateinit var etDateTime: Button
    var notificationTimeMillis: Long = 0
    var notificationId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        etDateTime = binding.timePickerButton
        etDateTime.setOnClickListener {

        }

        if (taskItem != null) {
            if (taskItem!!.isCompleted())
                binding.taskTitle.text = "Finished Task"
            else
                binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime() != null) {
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        } else {
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }

        binding.deleteButton.setOnClickListener {
            deleteAction()
        }

        // Create the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
        //send notification

    }

    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if (dueTime == null) null else Task.timeFormatter.format(dueTime)
        val dueDateString = if (dueDate == null) null else Task.dateFormatter.format(dueDate)
        if (taskItem == null) {
            val newTask = Task(name, desc, dueTimeString, dueDateString, null)
            taskViewModel.addTask(newTask)
        } else {
            taskItem!!.name = name
            taskItem!!.desc = name
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dueDateString = dueDateString

            taskViewModel.updateTask(taskItem!!)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

    private fun deleteAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if (dueTime == null) null else Task.timeFormatter.format(dueTime)
        val dueDateString = if (dueDate == null) null else Task.dateFormatter.format(dueDate)

        if (taskItem != null) {
            taskItem!!.name = name
            taskItem!!.desc = name
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dueDateString = dueDateString

            taskViewModel.deleteTask(taskItem!!)
        }
    }

    //create notifications
//    private fun createNotification(): Notification {
//        val notificationIntent = Intent(context, MyReceiver::class.java)
//        notificationIntent.putExtra("Task Due", "value")
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            notificationId,
//            notificationIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//        return NotificationCompat.Builder(requireContext(), "channel_id")
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle("Task Due!")
//            .setContentText("Notification Text")
//            //.setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//            .build()
//    }


//    private fun scheduleNotification() {
//        if (dueTime != null && dueDate != null) {
//            val calendar = Calendar.getInstance()
//            calendar.set(
//                dueDate!!.year,
//                dueDate!!.monthValue - 1,  // Adjust for 0-based month
//                dueDate!!.dayOfMonth,
//                dueTime!!.hour,
//                dueTime!!.minute
//            )
//
//            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
//            val notificationIntent = Intent(context, MyReceiver::class.java)
//
//            // Add dueTime and dueDate as extras in the intent
//            notificationIntent.putExtra("due_time", dueTime.toString())
//            notificationIntent.putExtra("due_date", dueDate.toString())
//
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                notificationId,
//                notificationIntent,
//                PendingIntent.FLAG_IMMUTABLE
//            )
//
//            notificationTimeMillis = calendar.timeInMillis
//
//            // Set the time at which the notification should be shown
//            if (alarmManager != null) {
//                alarmManager.set(
//                    AlarmManager.RTC_WAKEUP,
//                    notificationTimeMillis,
//                    pendingIntent
//                )
//            }
//        }
//
//    }
}
