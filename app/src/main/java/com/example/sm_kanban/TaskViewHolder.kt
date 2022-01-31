package com.example.sm_kanban

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskViewHolder(view: View, private val tab: TabFragment): RecyclerView.ViewHolder(view) {
    var model: Task = Task(status = TaskStatus.Todo, title = "UnInitialized!")
        get() = field
        set(value) {
            field = value
            titleView.text = field.title
            descriptionView.text = field.details

            leftButton.visibility = if(field.status == TaskStatus.Todo) View.INVISIBLE else View.VISIBLE
            rightButton.visibility = if(field.status == TaskStatus.Completed) View.INVISIBLE else View.VISIBLE

            deleteButton.setOnClickListener {
                deleteDialog.setPositiveButton(R.string.delete_dialog_positive)
                { _, _ ->
                    tab.deleteTask(adapterPosition)
                }.create().show()
            }
            leftButton.setOnClickListener { model.status = TaskStatus.get(model.status.type - 1); tab.moveTask(model, adapterPosition)}
            rightButton.setOnClickListener { model.status = TaskStatus.get(model.status.type + 1); tab.moveTask(model, adapterPosition)}
        }
    var titleView: TextView
    var descriptionView: TextView
    var leftButton: ImageButton
    var deleteButton: ImageButton
    var rightButton: ImageButton
//    val view = tab.requireView()

    init {
        titleView = view.findViewById(R.id.titleText)
        descriptionView = view.findViewById(R.id.descriptionText)
        leftButton = view.findViewById(R.id.leftButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        rightButton = view.findViewById(R.id.rightButton)
    }

    val deleteDialog : MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(tab.requireContext())
        .setTitle(R.string.delete_dialog_title)
        .setMessage(R.string.delete_dialog_text)
//        .setPositiveButton(R.string.delete_dialog_positive)
        .setNegativeButton(R.string.delete_dialog_negative)
        { _, _ -> }
}