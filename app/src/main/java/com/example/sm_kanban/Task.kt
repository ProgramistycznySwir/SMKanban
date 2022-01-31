package com.example.sm_kanban

import java.util.*

data class Task(val ID: Int = 0,
        var updated: Boolean = false,
        var status: TaskStatus,
        var title: String = "New Task",
        var details: String = "Task details")
