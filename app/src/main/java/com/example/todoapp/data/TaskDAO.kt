package com.example.todoapp.data

import androidx.lifecycle.LiveData

import androidx.room.*

@Dao
interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("select * from tasks_table")
    fun getAllTasks(): LiveData<List<Task>>

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun updateTask(task: Task)

    @Query("select COUNT(*) from tasks_table")
    fun getNumberOfTotalTasks():LiveData<Int>
}