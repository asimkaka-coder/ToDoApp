package com.example.todoapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Task
import com.example.todoapp.other.Resource
import com.example.todoapp.repositories.TaskRepository
import kotlinx.coroutines.launch
import java.util.*


class MainViewModel @ViewModelInject constructor(
    val repository: TaskRepository
):ViewModel() {
    lateinit var tasks:LiveData<List<Task>>

    lateinit var totalNumberOfTasks:LiveData<Int>

    private val _timeOfDay =MutableLiveData<String>()
    val timeOfDay:LiveData<String> = _timeOfDay


    val calendar = Calendar.getInstance()




    init {
        getMorningText(calendar)
        getAllTasks()
        totalNumberOfTasks = repository.getTotalTasks()
    }
    fun addTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun removeTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

//    fun updateTask(task:Task) = viewModelScope.launch {
//        repository.updateTask(task)
//    }

    fun getAllTasks() {
        viewModelScope.launch {
            tasks = repository.getAllTasks()
        }
    }

    fun getMorningText(calendar: Calendar){

        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

            _timeOfDay.value = when(hourOfDay){
                in 0..11 ->"Good Morning"
                in 12..15->"Good Afternoon"
                in 16..20->"Good Evening"
                in 20..24 -> "Good Night"
                else -> "Hello Seski"
            }

    }






}