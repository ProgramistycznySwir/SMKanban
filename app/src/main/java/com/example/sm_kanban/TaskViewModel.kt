package com.example.sm_kanban

import androidx.lifecycle.ViewModel
import java.util.*

class TaskViewModel : ViewModel() {
        var tasks = HashMap<TaskStatus, LinkedList<Task>>()
}
