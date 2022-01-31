package com.example.sm_kanban

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

class AppDB (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(AppContract.SQL_CREATE_TABLE_TASKS)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(AppContract.SQL_DROP_TABLE_TASKS)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun getTasks(): HashMap<TaskStatus, LinkedList<Task>> {
        var result: HashMap<TaskStatus, LinkedList<Task>> = HashMap<TaskStatus, LinkedList<Task>>()
        result[TaskStatus.Todo] = LinkedList<Task>()
        result[TaskStatus.InProgress] = LinkedList<Task>()
        result[TaskStatus.Completed] = LinkedList<Task>()

        val SQL_QUERY = "SELECT * FROM '${AppContract.TaskEntry.TABLE_NAME}'"
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery(SQL_QUERY, null)
        if(cursor.moveToFirst())
             do {
                val task = Task(
                    ID = cursor.getLong(0),
                    status = TaskStatus.get(cursor.getInt(1)),
                    title = cursor.getString(2),
                    details = cursor.getString(3)
                )
                result[task.status]!!.add(task)
            } while(cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }
    fun addTask(task: Task) {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues: ContentValues = AppDB.createEntry(task)
//        task.ID = db.insert(AppContract.TaskEntry.TABLE_NAME, null, contentValues)
        try {
//            mid = sqdb.insertOrThrow("Medicine", null, values);
            task.ID = db.insertOrThrow(AppContract.TaskEntry.TABLE_NAME, null, contentValues)
        }
        catch(e: SQLException) {
            Log.e("Exception","SQLException"+e.message);
            e.printStackTrace();
        }
        db.close()
    }
    fun updateTask(task: Task) {
        val db: SQLiteDatabase = this.writableDatabase
        db.update(AppContract.TaskEntry.TABLE_NAME, createEntry(task), "${AppContract.TaskEntry.COLUMN_ID}=?", arrayOf(task.ID.toString()))
//        db.delete(AppContract.TaskEntry.TABLE_NAME, "${AppContract.TaskEntry.COLUMN_ID}=?", arrayOf(task.ID.toString()))
        task.modified = false;
        db.close()
    }
    fun removeTask(task: Task) {
//        val SQL_QUERY = "DELETE FROM ${AppContract.TaskEntry.TABLE_NAME} WHERE ${AppContract.TaskEntry.COLUMN_ID}=${task.ID}"
        val db: SQLiteDatabase = this.writableDatabase
        db.delete(AppContract.TaskEntry.TABLE_NAME, "${AppContract.TaskEntry.COLUMN_ID}=?", arrayOf(task.ID.toString()))
        db.close()
    }



    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SMKanban.db"
        fun createEntry(task: Task): ContentValues {
            return  ContentValues().apply {
//                put(AppContract.TaskEntry.COLUMN_ID, task.ID)
                put(AppContract.TaskEntry.COLUMN_STATUS, task.status.type)
                put(AppContract.TaskEntry.COLUMN_TITLE, task.title)
                put(AppContract.TaskEntry.COLUMN_DETAILS, task.details)
            }
        }
    }
}