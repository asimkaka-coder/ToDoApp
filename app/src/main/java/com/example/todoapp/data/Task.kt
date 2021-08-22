package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Entity(tableName = "tasks_table")
data class Task(
    var content:String,
    var date:String
):Serializable {
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}