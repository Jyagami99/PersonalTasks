package com.example.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TaskRvAdapter
import com.example.personaltasks.controller.TaskController
import com.example.personaltasks.databinding.ActivityMainBinding
import com.example.personaltasks.model.Constant.EXTRA_TASK
import com.example.personaltasks.model.Constant.EXTRA_VIEW_TASK
import com.example.personaltasks.model.Task

class MainActivity : AppCompatActivity(), OnTaskClickListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val taskList: MutableList<Task> = mutableListOf()

    private val taskAdapter: TaskRvAdapter by lazy {
        TaskRvAdapter(taskList, this)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    private val taskController: TaskController by lazy {
        TaskController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.task_list)

        carl =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra(EXTRA_TASK, Task::class.java)
                    } else {
                        result.data?.getParcelableExtra<Task>(EXTRA_TASK)
                    }

                    task?.let { recivedTask ->
                        val position = taskList.indexOfFirst { it.id == recivedTask.id }
                        if (position == -1) {
                            taskList.add(recivedTask)
                            taskAdapter.notifyItemInserted(taskList.lastIndex)
                            taskController.insertTask(recivedTask)
                        } else {
                            taskList[position] = recivedTask
                            taskAdapter.notifyItemChanged(position)
                            taskController.updateTask(recivedTask)
                        }
                    }
                }
            }
        amb.recyclerView.adapter = taskAdapter
        amb.recyclerView.layoutManager = LinearLayoutManager(this)

        fillTaskList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_new_task -> {
                carl.launch(Intent(this, TaskFormActivity::class.java))
                true
            }

            else -> {
                false
            }
        }
    }

    override fun onTaskClick(position: Int) {
        Intent(this, TaskFormActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            putExtra(EXTRA_VIEW_TASK, true)
            startActivity(this)
        }
    }

    override fun onRemoveClick(position: Int) {
        taskController.deleteTask(taskList[position])
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        Toast.makeText(this, getString(R.string.task_removed), Toast.LENGTH_SHORT).show()
    }

    override fun onEditClick(position: Int) {
        Intent(this, TaskFormActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            carl.launch(this)
        }
    }

    private fun fillTaskList() {
        taskList.clear()
        Thread {
            taskList.addAll(taskController.retrieveTasks())
            taskAdapter.notifyDataSetChanged()

        }.start()
    }

}