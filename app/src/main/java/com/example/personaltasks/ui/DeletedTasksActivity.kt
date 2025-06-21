package com.example.personaltasks.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.DeletedTaskAdapter
import com.example.personaltasks.controller.TaskController
import com.example.personaltasks.model.Constant.EXTRA_TASK
import com.example.personaltasks.model.Task

class DeletedTasksActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: DeletedTaskAdapter
    private val taskController = TaskController()
    private val deletedTasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deleted_tasks)

        recyclerView = findViewById(R.id.recyclerViewDeleted)
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskAdapter = DeletedTaskAdapter(
            deletedTasks,
            onItemClick = { task -> showTaskDetails(task) },
            onItemLongClick = { view, task -> showContextMenu(view, task) })

        recyclerView.adapter = taskAdapter
        loadDeletedTasks()
    }

    private fun loadDeletedTasks() {
        taskController.retrieveTasks(deleted = true) { tasks ->
            deletedTasks.clear()
            deletedTasks.addAll(tasks)
            runOnUiThread { taskAdapter.notifyDataSetChanged() }
        }
    }

    private fun showTaskDetails(task: Task) {
        val intent = Intent(this, TaskFormActivity::class.java)
        intent.putExtra(EXTRA_TASK, task)
        intent.putExtra("action", "DETAILS")
        startActivity(intent)
    }

    private fun showContextMenu(view: View, task: Task) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.deleted_task_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_restore -> restoreTask(task)
                R.id.menu_details -> showTaskDetails(task)
            }
            true
        }
        popup.show()
    }

    private fun restoreTask(task: Task) {
        taskController.restoreTask(task)
        Toast.makeText(this, "Tarefa reativada", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }
}