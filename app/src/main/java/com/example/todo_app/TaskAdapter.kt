package com.example.todo_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private val tasks: MutableList<task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTaskTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewTaskDescription)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.date)
        val completeButton: Button = itemView.findViewById(R.id.buttonCompleteTask)
        val editButton: Button = itemView.findViewById(R.id.buttonEditTask)
        val deleteButton: Button = itemView.findViewById(R.id.buttonDeleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return TaskViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title
        holder.descriptionTextView.text = task.description
        holder.date.text = task.dateTime

        holder.completeButton.setOnClickListener {
            // Implement task completion logic
            task.isCompleted = true
            notifyDataSetChanged() // Update RecyclerView
        }

        holder.editButton.setOnClickListener {
        }

        holder.deleteButton.setOnClickListener {
            tasks.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


}