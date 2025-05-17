package com.example.personaltasks.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    suspend fun createTask(task: Task): Long
    @Update
    suspend fun updateTask(task: Task): Int
    @Delete
    suspend fun deleteTask(task: Task): Int
    @Query(value = "SELECT * FROM Task WHERE id = :id")
    fun retrieveTask(id: Int): Task
    @Query(value = "SELECT * FROM Task")
    fun retrieveTasks(): MutableList<Task>
}