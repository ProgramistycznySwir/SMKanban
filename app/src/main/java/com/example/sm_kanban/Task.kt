package com.example.sm_kanban

import java.util.*

data class Task(val ID: UUID = UUID.randomUUID(),
        var status: TaskStatus,
        var title: String = "New Task",
        var details: String = "")
