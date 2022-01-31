package com.example.sm_kanban

import java.util.*

interface Callbacks{
    fun addTask(task: Task)
    fun updateTask(task: Task)
    fun removeTask(tasklistType: TaskStatus, index: Int)
    fun getTasks(tasklistType: TaskStatus) : LinkedList<Task>
}