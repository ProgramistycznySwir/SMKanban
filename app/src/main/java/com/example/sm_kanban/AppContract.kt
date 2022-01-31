package com.example.sm_kanban

import android.provider.BaseColumns

object AppContract {
    // Table contents are grouped together in an anonymous object.
    object TaskEntry : BaseColumns {
        const val TABLE_NAME = "Tasks"
        const val COLUMN_ID = "id"
        const val COLUMN_STATUS = "status"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DETAILS = "details"
    }

    val SQL_CREATE_TABLE_TASKS =
        "CREATE TABLE ${TaskEntry.TABLE_NAME} (" +
                "${TaskEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${TaskEntry.COLUMN_STATUS} INTEGER," +
                "${TaskEntry.COLUMN_TITLE} TEXT," +
                "${TaskEntry.COLUMN_DETAILS} TEXT" +
                ")"

    val SQL_DROP_TABLE_TASKS = "DROP TABLE IF EXISTS ${TaskEntry.TABLE_NAME}"
}