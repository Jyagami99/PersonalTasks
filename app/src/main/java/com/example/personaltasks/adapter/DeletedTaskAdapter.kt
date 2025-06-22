package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Task

class DeletedTaskAdapter(
    private val tasks: List<Task>,
    private val onItemClick: (Task) -> Unit,
    private val onItemLongClick: (View, Task) -> Unit
) : RecyclerView.Adapter<DeletedTaskAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.taskTitle)
        private val deadline: TextView = itemView.findViewById(R.id.taskDeadline)

        fun bind(task: Task) {
            title.text = task.title
            deadline.text = task.deadline

            itemView.setOnClickListener {
                onItemClick(task)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(it, task)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}
