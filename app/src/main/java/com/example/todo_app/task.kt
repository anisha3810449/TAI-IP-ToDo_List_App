package com.example.todo_app

data class task(var title: String,
                var description: String,
                var dateTime: String,
                var isCompleted: Boolean = false)
