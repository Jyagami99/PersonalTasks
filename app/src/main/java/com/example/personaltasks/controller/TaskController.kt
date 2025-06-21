package com.example.personaltasks.controller

import android.util.Log
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskFirebase

class TaskController {
    private val repository = TaskFirebase()

    fun insertTask(task: Task) {
        repository.addTask(task) { success ->
            if (!success) {
                Log.e("TaskController", "Erro ao inserir tarefa no Firebase")
            }
        }
    }

    fun updateTask(task: Task) {
        repository.updateTask(task) { success ->
            if (!success) {
                Log.e("TaskController", "Erro ao atualizar tarefa no Firebase")
            }
        }
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task) { success ->
            if (!success) {
                Log.e("TaskController", "Erro ao excluir tarefa no Firebase")
            }
        }
    }

    fun restoreTask(task: Task) {
        repository.restoreTask(task) { success ->
            if (!success) {
                Log.e("TaskController", "Erro ao restaurar tarefa no Firebase")
            }
        }
    }

    fun retrieveTasks(deleted: Boolean, onResult: (List<Task>) -> Unit) {
        repository.getTasks(deleted, onResult)
    }
}