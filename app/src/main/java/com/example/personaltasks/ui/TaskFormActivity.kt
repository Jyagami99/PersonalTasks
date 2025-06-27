package com.example.personaltasks.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityTaskFormBinding
import com.example.personaltasks.model.Constant.EXTRA_TASK
import com.example.personaltasks.model.Constant.EXTRA_VIEW_TASK
import com.example.personaltasks.model.Task
import java.util.Calendar

class TaskFormActivity : AppCompatActivity() {
    private val binding: ActivityTaskFormBinding by lazy {
        ActivityTaskFormBinding.inflate(layoutInflater)
    }

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Nova Tarefa"

        val action = intent.getStringExtra("action")
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
                inputStatus.setText(it.status)
                //inputPriority.setText(it.priority)

                val isViewOnly = intent.getBooleanExtra(EXTRA_VIEW_TASK, false) || action == "DETAILS"
                if (isViewOnly) {
                    supportActionBar?.subtitle = "Vizualizar Tarefa"
                    inputTitle.isEnabled = false
                    inputDescription.isEnabled = false
                    inputDeadline.isEnabled = false
                    inputStatus.isEnabled = false
                    prioritySpinner.isEnabled = false
                    //inputPriority.isEnabled = false
                    btnSave.visibility = View.GONE
                    btnCancel.text = "Voltar"

                    currentFocus?.clearFocus()
                    window.setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                }
            }
        }

        binding.inputDeadline.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                binding.inputDeadline.setText(formattedDate)
            }, year, month, day).show()
        }

        with(binding) {
            btnSave.setOnClickListener {
                Task(
                    id = receiveTask?.id ?: hashCode(),
                    title = inputTitle.text.toString(),
                    description = inputDescription.text.toString(),
                    deadline = inputDeadline.text.toString(),
                    status = inputStatus.text.toString(),
                    priority = prioritySpinner.toString()
                    //priority = inputPriority.text.toString()

                ).let { task ->
                    Intent().apply {
                        putExtra(EXTRA_TASK, task)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
            btnCancel.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }
}