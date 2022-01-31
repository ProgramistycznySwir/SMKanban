package com.example.sm_kanban

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
public const val ARG_NAME_taskListType = "tasklistType"

/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    lateinit var taskListType: TaskStatus

    lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskListAdapter : TaskListAdapter
    public var callbacks: Callbacks? = null
        public get() = field
        private set(value) { field = value }

    //Attach context as a Callbacks reference to the callbacks variable when fragment attaches to container
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    //Detach context (assign to null) when fragment detaches from container
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskListType = TaskStatus.values()[it.getInt(ARG_NAME_taskListType)]
        }
        (context as MainActivity).registerTabFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
//        itemTouchHelper.attachToRecyclerView(taskRecyclerView)
        updateInterface()
        return view
    }

    private fun updateInterface(){
        val tasks = callbacks!!.getTasks(taskListType)
        taskListAdapter = TaskListAdapter(tasks, this)
        taskRecyclerView.adapter = taskListAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TabFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(taskStatus: TaskStatus) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NAME_taskListType, taskStatus as Int)
                }
            }
    }

    fun deleteTask(index: Int) {
        callbacks?.removeTask(taskListType, index)
        taskListAdapter.notifyItemRemoved(index)
    }
    fun addTask(task: Task) {
        callbacks?.addTask(task)
    }
    fun moveTask(task: Task, index: Int) {
        addTask(task)
        deleteTask(index)
    }

    fun notifyAdded(index: Int) {
        taskListAdapter.notifyItemInserted(index)
    }

    private inner class TaskListAdapter(var taskList: LinkedList<Task>, val tabFragment: TabFragment)
            : RecyclerView.Adapter<TaskViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_task, parent, false)
            return TaskViewHolder(view, tabFragment)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            holder.model = taskList[position]
        }

        override fun getItemCount(): Int = taskList.size
    }
}