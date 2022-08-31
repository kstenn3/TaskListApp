package com.kylestennfeld.tasklistfinal.Utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kylestennfeld.tasklistfinal.Model.TaskViewModel


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG = "DatabaseHandler"
    companion object{
        const val TABLE_NAME = "taskTable"
        const val COL_ID = "_id"
        const val COL_TASK = "task"
        const val COL_STATUS = "status"

        private const val DATABASE_NAME = "TasksDatabase"
        private const val DATABASE_VERSION = 1

    }

    /* creates table with TaskViewModel attributes */
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME(" +
                "$COL_ID INTEGER PRIMARY KEY," +
                "$COL_TASK TEXT," +
                "$COL_STATUS INTEGER)"

        p0?.execSQL(CREATE_TABLE_QUERY)
    }

    /* upgrade table with new documents */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    /** insert document into database **/
    fun addTask(item: TaskViewModel): Long {
        val db = this.writableDatabase

        /* get values for document using a TaskViewModel object */
        val cv = ContentValues()
        cv.put(COL_TASK, item.task)
        cv.put(COL_STATUS, item.status)

        /* insert document into database */
        val success = db.insert(TABLE_NAME, null, cv)

        db.close()
        return success
    }

    /** read data from the database **/
    @SuppressLint("Range")
    fun viewData(): ArrayList<TaskViewModel> {
        val taskList: ArrayList<TaskViewModel> = ArrayList()
        var id: Int
        var task: String
        var status: Int

        val db = this.readableDatabase
        var cursor: Cursor?

        /* query selects all documents from database */
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        db.beginTransaction()
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d(TAG, "viewData: oh no")
            db.execSQL(selectQuery)
            return ArrayList()
        }

        /* move cursor to each entry until no entries are left*/
        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                task = cursor.getString(cursor.getColumnIndex(COL_TASK))
                status = cursor.getInt(cursor.getColumnIndex(COL_STATUS))

                val item = TaskViewModel(id, task, status)
                taskList.add(item)
            } while(cursor.moveToNext())
        }
        db.endTransaction()
        cursor.close()
        return taskList
    }

    // NOTE: not planing on implementing this
    /** update/edit document found in database**/
    fun updateData(item: TaskViewModel): Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TASK, item.task)
        cv.put(COL_STATUS, item.status)

        /* update document data. use whereClause to fetch document by id */
        val success = db.update(TABLE_NAME, cv, "$COL_ID=${item.id}", null)

        db.close()
        return success
    }

    /** update status of the check mark */
    fun updateCheckBox(item: TaskViewModel, id: Int, status: Int): Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_STATUS, status)
        val success = db.update(TABLE_NAME, cv, "$COL_ID=${item.id}", null)
        db.close()
        return success
    }

    /** delete document from database */
    fun deleteData(item: TaskViewModel): Int {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, item.id)

        /* delete document by using whereClause to get document by ID */
        val success = db.delete(TABLE_NAME, "$COL_ID=${item.id}", null)

        db.close()
        return success
    }

}