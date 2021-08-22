package com.example.todoapp.ui

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_task_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddFragment : Fragment(R.layout.add_task_fragment) {

    val viewModel: MainViewModel by viewModels()
    val args: AddFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = args.currentTask?.let { updatedTask ->
            edit_task_item.setText(updatedTask.content)
            add_button.visibility = View.GONE
            add_button.isEnabled = false
            delete_button.visibility = View.VISIBLE
            delete_button.isEnabled = true
            return@let updatedTask

        }



        update_button.setOnClickListener {
            task?.content = edit_task_item.text.toString()
            task?.date = getCurrentDate()
            Log.d("AddF", task?.content.toString())
            if (task != null && task.content.isNotEmpty() && task.content.isNotBlank()) {
                viewModel.addTask(task)
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)

            }else{
                Toast.makeText(requireContext(),"Enter some text!",Toast.LENGTH_LONG).show()
            }
        }

        if(task==null){
            delete_button.visibility = View.GONE
            delete_button.isEnabled = false
        }

        delete_button.setOnClickListener {
            if (task != null) {
                viewModel.removeTask(task)
                Toast.makeText(requireContext(), "Deleted Current Task", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }
        }



        add_button.setOnClickListener {
            val content = edit_task_item.text.toString()
            if (content.isNotEmpty() && content.isNotBlank()) {
                val newTask = Task(content,getCurrentDate() )
                viewModel.addTask(newTask)
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }else{
                Toast.makeText(requireContext(),"Enter some text!",Toast.LENGTH_LONG).show()
            }
        }


        back_button.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd' Time:'HH:mm:ss")
        return sdf.format(Date())
    }
}