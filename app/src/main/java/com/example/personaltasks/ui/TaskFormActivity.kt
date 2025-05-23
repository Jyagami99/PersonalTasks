package com.example.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityTaskFormBinding
import com.example.personaltasks.model.Constant.EXTRA_TASK
import com.example.personaltasks.model.Constant.EXTRA_VIEW_TASK
import com.example.personaltasks.model.Task

class TaskFormActivity : AppCompatActivity() {
    private val binding: ActivityTaskFormBinding by lazy {
        ActivityTaskFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Nova Tarefa"

        val receiveTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
        } else {
            intent.getParcelableExtra<Task>(EXTRA_TASK)
        }

        receiveTask?.let {
            supportActionBar?.subtitle = "Editando Tarefa"
            with(binding) {
                inputTitle.setText(it.title)
                inputDescription.setText(it.description)
                inputDeadline.setText(it.deadline)

                val viewTask = intent.getBooleanExtra(EXTRA_VIEW_TASK, false)
                if (viewTask) {
                    supportActionBar?.subtitle = "Vizualizar Tarefa"
                    inputTitle.isEnabled = false
                    inputDescription.isEnabled = false
                    inputDeadline.isEnabled = false
                    btnSave.text = "OK"
                    btnSave.visibility = View.GONE
                }
            }
        }

        with(binding) {
            btnSave.setOnClickListener {
                Task(
                    id = receiveTask?.id ?: hashCode(),
                    title = inputTitle.text.toString(),
                    description = inputDescription.text.toString(),
                    deadline = inputDeadline.text.toString()

                ).let { task ->
                    Intent().apply {
                        putExtra(EXTRA_TASK, task)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
        }
    }
}