package com.example.sm_kanban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity(), Callbacks {

    private lateinit var taskViewModel: TaskViewModel
    private var tabFrags: HashMap<TaskStatus, TabFragment> = HashMap()
    fun registerTabFragment(tabFrag: TabFragment) {
        tabFrags[tabFrag.taskListType] = tabFrag
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Get viewmodel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        DEBUG_createExampleViewModel()
    }

    //When activity is stopped, write all viewmodel list data to shared prefs
    override fun onStop() {
        super.onStop()
        //TODO: Save data
    }


    //region >>> Callbacks Implementation <<<
    override fun addTaskToViewModel(task: Task) {
        val taskList = getTaskListFromViewModel(task.status)
        taskList.add(task)
        tabFrags[task.status]?.notifyAdded(taskList!!.size)
    }

    override fun deleteTaskFromViewModel(type: TaskStatus, index: Int) {
        getTaskListFromViewModel(type).removeAt(index)
    }

    override fun getTaskListFromViewModel(tasklistType: TaskStatus): LinkedList<Task> {
        return taskViewModel.tasks[tasklistType]!!
    }
    //endregion



    private fun DEBUG_createExampleViewModel() {
        taskViewModel.tasks = HashMap<TaskStatus, LinkedList<Task>>()
        taskViewModel.tasks[TaskStatus.Todo] = LinkedList<Task>()
        taskViewModel.tasks[TaskStatus.Todo]?.addAll(listOf(Task(status = TaskStatus.Todo, title = "Task1"),
            Task(status = TaskStatus.Todo, title = "Task4")))
        taskViewModel.tasks[TaskStatus.InProgress] = LinkedList<Task>()
        taskViewModel.tasks[TaskStatus.InProgress]?.addAll(listOf(Task(status = TaskStatus.InProgress, title = "Task2")))
        taskViewModel.tasks[TaskStatus.Completed] = LinkedList<Task>()
        taskViewModel.tasks[TaskStatus.Completed]?.addAll(listOf(Task(status = TaskStatus.Completed, title = "Task3")))
    }
}