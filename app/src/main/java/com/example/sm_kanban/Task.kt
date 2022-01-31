package com.example.sm_kanban

import java.util.*

data class Task(var ID: Long = 0,
                var modified: Boolean = false, // TODO Delete this field!
                var status: TaskStatus,
                var title: String = "New Task",
                var details: String = "Task details")
