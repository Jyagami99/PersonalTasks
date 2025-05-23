package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.databinding.ItemTaskBinding
import com.example.personaltasks.model.Task
import com.example.personaltasks.ui.OnTaskClickListener

class TaskRvAdapter(
    private val tasks: MutableList<Task>, private val onTaskClick: OnTaskClickListener
) : RecyclerView.Adapter<TaskRvAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemTaskBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemTaskBinding.root) {
        val tvTitle = itemTaskBinding.taskTitle
        val tvDeadline = itemTaskBinding.taskDeadline

        init {
            itemTaskBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                (onTaskClick as AppCompatActivity).menuInflater.inflate(R.menu.context_menu, menu)
                menu.findItem(R.id.remove_task_mi).setOnMenuItemClickListener {
                    onTaskClick.onRemoveClick(adapterPosition)
                    true
                }
                menu.findItem(R.id.edit_task_mi).setOnMenuItemClickListener {
                    onTaskClick.onEditClick(adapterPosition)
                    true
                }
                menu.findItem(R.id.detail_task_mi).setOnMenuItemClickListener {
                    onTaskClick.onTaskClick(adapterPosition)
                    true
                }
            }
            itemTaskBinding.root.setOnClickListener {
                onTaskClick.onTaskClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TaskViewHolder = TaskViewHolder(
        ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        tasks[position].let { task ->
            with(holder) {
                tvTitle.text = task.title
                tvDeadline.text = task.deadline
            }
        }
    }

    override fun getItemCount(): Int = tasks.size
}
