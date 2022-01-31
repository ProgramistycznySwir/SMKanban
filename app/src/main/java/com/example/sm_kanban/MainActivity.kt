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
//    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val intent = Intent(context, MainActivity::class.java)
//            baseContext.startActivity(intent)
//            finishAffinity()
//        }
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val lang = sharedPreferences.getString("language_preference", "default")
        if(lang != "default") {
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = baseContext.resources.configuration
            config.locale = locale
            baseContext.resources.updateConfiguration(
                config,
                baseContext.resources.displayMetrics
            )
        }
//        LocalBroadcastManager.getInstance(baseContext)
//            .registerReceiver(broadcastReceiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        loadData()
    }

    override fun onStop() {
        super.onStop()
        saveModifiedTasks()
//        updateData()
    }
    override fun onDestroy() {
        super.onDestroy()
//        saveModifiedTasks()
//        LocalBroadcastManager.getInstance(baseContext)
//            .unregisterReceiver(broadcastReceiver)

    }


    //region >>> Callbacks Implementation <<<
    override fun addTask(task: Task) {
        val taskList = getTasks(task.status)
        taskList.add(task)
        tabFrags[task.status]?.notifyAdded(taskList!!.size)
        val db: AppDB = AppDB(baseContext)
        db.addTask(task)
        db.close()
    }
    override fun updateTask(task: Task) {
        val db: AppDB = AppDB(baseContext)
        db.updateTask(task)
        db.close()
    }
    override fun removeTask(type: TaskStatus, index: Int) {
        val task = getTasks(type)[index]
        getTasks(type).removeAt(index)
        val db: AppDB = AppDB(baseContext)
        db.removeTask(task)
        db.close()
    }
    override fun getTasks(tasklistType: TaskStatus): LinkedList<Task> {
        return taskViewModel.tasks[tasklistType]!!
    }
    //endregion

    private fun loadData() {
        val db: AppDB = AppDB(baseContext)
        taskViewModel.tasks = db.getTasks()
        db.close()
//        DEBUG_createExampleViewModel()
    }

    private fun saveModifiedTasks() {
        val db: AppDB = AppDB(baseContext)
        for(list in taskViewModel.tasks.values)
            for(task in list)
                if(task.modified)
                    db.updateTask(task)
        db.close()
    }
}