package com.example.sm_kanban

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDB (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(AppContract.SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(AppContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        val dbHelper = FeedReaderDbHelper(context)
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SMKanban.db"
        fun createEntry(task: Task): ContentValues {
            return  ContentValues().apply {
                put(AppContract.TaskEntry.COLUMN_ID, task.ID)
                put(AppContract.TaskEntry.COLUMN_STATUS, task.status.type)
                put(AppContract.TaskEntry.COLUMN_TITLE, task.title)
                put(AppContract.TaskEntry.COLUMN_DETAILS, task.details)
            }
        }
    }
}