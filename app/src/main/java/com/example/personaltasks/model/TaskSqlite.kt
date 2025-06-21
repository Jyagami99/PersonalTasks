package com.example.personaltasks.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.sql.SQLException

class TaskSqlite(context: Context) : TaskDao {
    private val TASK_DB_FILE = "personalTask"
    private val TASK_TABLE = "task"
    private val TASK_ID = "id"
    private val TASK_TITLE = "title"
    private val TASK_DESCRIPTION = "description"
    private val TASK_DEADLINE = "deadline"
    private val TASK_STATUS = "status"

    val CREATE_TASK_STATEMENT = "CREATE TABLE IF NOT EXISTS $TASK_TABLE (" +
            "$TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "$TASK_TITLE TEXT NOT NULL, " +
            "$TASK_DESCRIPTION TEXT NOT NULL, " +
            "$TASK_DEADLINE TEXT NOT NULL, " +
            "$TASK_STATUS TEXT NOT NULL);"

    private val db: SQLiteDatabase =
        context.openOrCreateDatabase(TASK_DB_FILE, Context.MODE_PRIVATE, null)

    init {
        try {
            db.execSQL(CREATE_TASK_STATEMENT)
        } catch (se: SQLException) {
            Log.e("TaskSqlite", se.message.toString())
        }
    }

    override suspend fun createTask(task: Task) =
        db.insert(TASK_TABLE, null, task.toContentValues())

    override suspend fun updateTask(task: Task) = db.update(
        TASK_TABLE,
        task.toContentValues(),
        "$TASK_ID = ?",
        arrayOf(task.id.toString())
    )


    override suspend fun deleteTask(task: Task): Int {
        return db.delete(
            TASK_TABLE,
            "$TASK_ID = ?",
            arrayOf(task.id.toString())
        )
    }

    override fun retrieveTasks(): MutableList<Task> {
        val tasks: MutableList<Task> = mutableListOf()
        val cursor = db.rawQuery(
            "SELECT * FROM $TASK_TABLE",
            null
        )

        while (cursor.moveToNext()) {
            tasks.add(cursor.toTask())
        }

        return tasks
    }

    override fun retrieveTask(id: Int): Task {
        val cursor = db.query(
            true,
            TASK_TABLE,
            null,
            "$TASK_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            cursor.toTask()
        } else {
            Task()
        }
    }

    private fun Task.toContentValues() = ContentValues().apply {
        put(TASK_TITLE, title)
        put(TASK_DESCRIPTION, description)
        put(TASK_DEADLINE, deadline)
        put(TASK_STATUS, status)
    }

    private fun Cursor.toTask() = Task(
        getInt(getColumnIndexOrThrow(TASK_ID)),
        getString(getColumnIndexOrThrow(TASK_TITLE)),
        getString(getColumnIndexOrThrow(TASK_DESCRIPTION)),
        getString(getColumnIndexOrThrow(TASK_DEADLINE)),
        getString(getColumnIndexOrThrow(TASK_STATUS)),
    )
}