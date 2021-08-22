package com.example.todoapp.repositories

import com.example.todoapp.data.Task
import com.example.todoapp.data.TaskDAO
import javax.inject.Inject

class TaskRepository @Inject constructor(
    val dao: TaskDAO
) {

    suspend fun insertTask(task: Task) = dao.insertTask(task)

    suspend fun deleteTask(task: Task) = dao.deleteTask(task)

//    suspend fun updateTask(task: Task) = dao.updateTask(task)

    fun getAllTasks() = dao.getAllTasks()

    fun getTotalTasks() = dao.getNumberOfTotalTasks()
}