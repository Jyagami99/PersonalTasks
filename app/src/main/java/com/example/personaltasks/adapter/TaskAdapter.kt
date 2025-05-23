package com.example.personaltasks.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.personaltasks.R
import com.example.personaltasks.databinding.ItemTaskBinding
import com.example.personaltasks.model.Task

class TaskAdapter(context: Context, private val tasks: MutableList<Task>) :
    ArrayAdapter<Task>(context, R.layout.item_task, tasks) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val task = tasks[position]

        var view = convertView
        if (view == null) {
            ItemTaskBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).apply {
                val itemTaskViewHolder = ItemTaskViewHolder(taskTitle, taskDeadline)
                view = root
                (view as LinearLayout).tag = itemTaskViewHolder
            }
        }

        val viewHolder = view?.tag as ItemTaskViewHolder
        viewHolder.taskTitle.text = task.title
        viewHolder.taskDeadline.text = task.deadline
        return view as View
    }

    private data class ItemTaskViewHolder(val taskTitle: TextView, val taskDeadline: TextView)
}