package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.Task
import com.example.todoapp.data.TaskDAO
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ):TaskDatabase = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "taskTable").build()


    @Singleton
    @Provides
    fun provideTaskDAO(taskDatabase: TaskDatabase):TaskDAO= taskDatabase.getTaskDao()

    fun provideRepository(dao: TaskDAO):TaskRepository = TaskRepository(dao)
}