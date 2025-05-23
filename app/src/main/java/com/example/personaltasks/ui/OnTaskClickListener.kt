package com.example.personaltasks.ui

sealed interface OnTaskClickListener {
    fun onTaskClick(position: Int)
    fun onRemoveClick(position: Int)
    fun onEditClick(position: Int)
}