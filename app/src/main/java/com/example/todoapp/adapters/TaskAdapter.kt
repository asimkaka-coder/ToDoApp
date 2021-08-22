package com.example.todoapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.Task
import kotlinx.android.synthetic.main.task_item_view.view.*

class TaskAdapter(): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {



    val differCallback = object :DiffUtil.ItemCallback<Task>(

    ){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
           return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.content == newItem.content
        }
    }

    val differ = AsyncListDiffer(this,differCallback)


    inner class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate( R.layout.task_item_view,parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val currentTask = differ.currentList[position]
        holder.itemView.apply {
            content_view.text = currentTask.content
            date_item.text = currentTask.date
            setOnClickListener {
                onItemlistener?.let {  it(currentTask)}
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemlistener:((Task)-> Unit)? = null

    fun setOnClickTaskListener(listener: (Task)->Unit){
        onItemlistener = listener
    }
}