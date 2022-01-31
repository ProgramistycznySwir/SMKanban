package com.example.sm_kanban

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import java.util.*

class MainActivity : AppCompatActivity(), Callbacks {

    private lateinit var taskViewModel: TaskViewModel
    private var tabFrags: HashMap<TaskStatus, TabFragment> = HashMap()
    fun registerTabFragment(tabFrag: TabFragment) {
        tabFrags[tabFrag.taskListType] = tabFrag
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val lang = sharedPreferences.getString("language_preference", "default")
        if(lang != "default") {
            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = baseContext.resources.configuration
            config.locale = locale
            baseContext.resources.updateConfiguration(
                config,
                baseContext.resources.displayMetrics
            )
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Get viewmodel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        loadData()
    }

    //When activity is stopped, write all viewmodel list data to shared prefs
    override fun onStop() {
        super.onStop()
        saveData()
    }


    //region >>> Callbacks Implementation <<<
    override fun addTask(task: Task) {
        val taskList = getTasks(task.status)
        taskList.add(task)
        tabFrags[task.status]?.notifyAdded(taskList!!.size)
    }

    override fun removeTask(type: TaskStatus, index: Int) {
        getTasks(type).removeAt(index)
    }

    override fun getTasks(tasklistType: TaskStatus): LinkedList<Task> {
        return taskViewModel.tasks[tasklistType]!!
    }
    //endregion

    private fun loadData() {
        DEBUG_createExampleViewModel()
    }
    private fun saveData() {
        //TODO: Save data
    }

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