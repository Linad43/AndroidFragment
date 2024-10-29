package com.example.androidfragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "TASKS_DATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "tasks_table"
        val KEY_ID = "id"
        val KEY_TEXT = "text"
        val KEY_FLAG = "flag"
        val KEY_DATA_CREATE = "data_create"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME " +
                "($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_TEXT TEXT, " +
                "$KEY_FLAG INT, " +
                "$KEY_DATA_CREATE TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun addTask(task: Task) {
        val values = ContentValues()
        values.put(KEY_ID, task.id)
        values.put(KEY_TEXT, task.text)
        values.put(KEY_FLAG, if (task.flag) 1 else 0)
        values.put(KEY_DATA_CREATE, task.dataCreate)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun removeAll() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    @SuppressLint("Recycle", "Range")
    fun readTasks(): ArrayList<Task> {
        val tasks = arrayListOf<Task>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return tasks
        }
        var taskId: Int
        var taskText: String
        var taskFlag: Int
        var taskDataCreate: String
        var task: Task
        if (cursor.moveToFirst()) {
            do {
                taskId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                taskText = cursor.getString(cursor.getColumnIndex(KEY_TEXT))
                taskFlag = cursor.getInt(cursor.getColumnIndex(KEY_FLAG))
                taskDataCreate = cursor.getString(cursor.getColumnIndex(KEY_DATA_CREATE))
                task = Task(taskId, taskText, taskFlag == 1, taskDataCreate)
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        return tasks
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.id)
        contentValues.put(KEY_TEXT, task.text)
        contentValues.put(KEY_FLAG, if (task.flag) 1 else 0)
        contentValues.put(KEY_DATA_CREATE, task.dataCreate)
        db.update(TABLE_NAME, contentValues, "id=" + task.id, null)
        db.close()
    }
    fun deleteTask(task: Task) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, task.id)
        db.delete(TABLE_NAME, "id=" + task.id, null)
        db.close()
    }
}