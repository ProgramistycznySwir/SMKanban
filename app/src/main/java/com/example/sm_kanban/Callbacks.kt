package com.example.sm_kanban

import java.util.*

interface Callbacks{
    fun addTaskToViewModel(task: Task)
    fun deleteTaskFromViewModel(tasklistType: TaskStatus, index: Int)
    fun getTaskListFromViewModel(tasklistType: TaskStatus) : LinkedList<Task>
}