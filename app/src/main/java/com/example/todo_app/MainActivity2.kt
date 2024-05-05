package com.example.todo_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity2 : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<task>()
    lateinit var recyclerViewTasks: RecyclerView
    lateinit var fabAddTask: ImageButton
    lateinit var editTextDate: EditText
    lateinit var editTextTime: EditText
    lateinit var buttonAddTask: Button
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
        fabAddTask = findViewById(R.id.fabAddTask)

        // Initialize RecyclerView and TaskAdapter
        taskAdapter = TaskAdapter(tasks)
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter

        // Add Task button click listener
        fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialog = AddTaskDialog(this) { title, description, dateTime ->
            val task = task(title, description, dateTime)
            addTask(task)
        }
        dialog.show()
    }

    inner class AddTaskDialog(context: Context, private val listener: (String, String, String) -> Unit) : Dialog(context) {
        private var selectedDate = Calendar.getInstance()
        private var selectedTime = Calendar.getInstance()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_add_task)

            editTextDate = findViewById(R.id.editTextDate)
            editTextTime = findViewById(R.id.editTextTime)
            buttonAddTask = findViewById(R.id.buttonAddTask)
            editTextTitle = findViewById(R.id.editTextTitle)
            editTextDescription = findViewById(R.id.editTextDescription)

            // Set current date and time initially
            updateDateTimeViews()

            editTextDate.setOnClickListener {
                showDatePicker()
            }

            editTextTime.setOnClickListener {
                showTimePicker()
            }

            buttonAddTask.setOnClickListener {
                val title = editTextTitle.text.toString().trim()
                val description = editTextDescription.text.toString().trim()
                val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(selectedDate.time)
                //val dateTime = editTextDate.text.toString().trim()
                if (title.isEmpty()) {
                    Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT).show()
                } else {
                    listener.invoke(title, description, dateTime)
                    dismiss()
                }
            }
        }

        private fun showTimePicker() {
            val timePickerDialog = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    updateDateTimeViews()
                },
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }

        private fun showDatePicker() {
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                selectedDate.set(year, monthOfYear, dayOfMonth)
                updateDateTimeViews()
            },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }

        private fun updateDateTimeViews() {
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

            editTextDate.setText(dateFormat.format(selectedDate.time))
            editTextTime.setText(timeFormat.format(selectedTime.time))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addTask(task: task) {
        tasks.add(task)
        taskAdapter.notifyDataSetChanged() // Update RecyclerView

    }
}