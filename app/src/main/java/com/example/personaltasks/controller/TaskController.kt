package com.example.personaltasks.controller

import androidx.room.Room
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskDao
import com.example.personaltasks.model.TaskRoomDb
import com.example.personaltasks.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskController(mainActivity: MainActivity) {
    private val taskDao: TaskDao = Room.databaseBuilder(
        context = mainActivity,
        klass = TaskRoomDb::class.java,
        name = "task_db"

    ).build().taskDao()

    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.createTask(task)
        }
    }

    fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.deleteTask(task)
        }
    }

    fun retrieveTask(id: Int): Task {
        return taskDao.retrieveTask(id)
    }

    fun retrieveTasks(): MutableList<Task> {
        return taskDao.retrieveTasks()
    }

}