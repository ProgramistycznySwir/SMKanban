package com.example.sm_kanban

import android.content.Context
import java.time.temporal.ValueRange

enum class TaskStatus(val type: Int) {
    Todo(0), InProgress(1), Completed(2);

    fun getLocalizedString(context: Context): String {
        return TaskStatus.getLocalizedString(context, type)
    }
    companion object{
        fun getLocalizedString(context: Context, type: Int): String = when(type) {
                0 -> context.getString(R.string.task_todo)
                1 -> context.getString(R.string.task_inProgress)
                2 -> context.getString(R.string.task_completed)
                else -> context.getString(R.string.error)
            }
        fun get(type: Int) = when(type) {
            0 -> Todo
            1 -> InProgress
            2 -> Completed
            else -> throw ArrayIndexOutOfBoundsException()
        }
    }
}