package com.example.personaltasks.model

import com.example.personaltasks.auth.AuthManager
import com.google.firebase.firestore.FirebaseFirestore

class TaskFirebase {
    private val db = FirebaseFirestore.getInstance()
    private val tasksCollection = db.collection("tasks")

    fun addTask(task: Task, onComplete: (Boolean) -> Unit) {
        val userId = AuthManager.getCurrentUserId() ?: return onComplete(false)
        val newDoc = tasksCollection.document()
        val newTask = task.copy(id = newDoc.id.hashCode())
        newDoc.set(newTask.copy(id = newTask.id, deleted = false))
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun updateTask(task: Task, onComplete: (Boolean) -> Unit) {
        tasksCollection
            .whereEqualTo("id", task.id)
            .get()
            .addOnSuccessListener { docs ->
                if (!docs.isEmpty) {
                    val doc = docs.first()
                    doc.reference.set(task)
                        .addOnSuccessListener { onComplete(true) }
                        .addOnFailureListener { onComplete(false) }
                } else {
                    onComplete(false)
                }
            }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteTask(task: Task, onComplete: (Boolean) -> Unit) {
        updateTask(task.copy(deleted = true), onComplete)
    }

    fun restoreTask(task: Task, onComplete: (Boolean) -> Unit) {
        updateTask(task.copy(deleted = false), onComplete)
    }

    fun getTasks(deleted: Boolean, onResult: (List<Task>) -> Unit) {
        tasksCollection
            .whereEqualTo("deleted", deleted)
            .get()
            .addOnSuccessListener { result ->
                val tasks = result.mapNotNull { it.toObject(Task::class.java) }
                onResult(tasks)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }
}
