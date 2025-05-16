package com.example.personaltasks.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface TaskDao {
    @Insert
    fun createTask(task: Task): Long
    @Update
    fun updateTask(task: Task): Int
    @Delete
    fun deleteTask(task: Task): Int
    @Query(value = "SELECT * FROM Task WHERE id = :id")
    fun retrieveTask(id: Int): Task
    @Query(value = "SELECT * FROM Task")
    fun retrieveTasks(): MutableList<Task>
}