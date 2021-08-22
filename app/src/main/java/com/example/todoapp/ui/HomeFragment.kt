package com.example.todoapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapters.TaskAdapter
import com.example.todoapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*


@AndroidEntryPoint
class HomeFragment:Fragment(R.layout.home_fragment) {

     val viewModel:MainViewModel by viewModels()

    lateinit var adapter: TaskAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        menu_icon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        add_task_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        setUpRecyclerView()


        val touchCallBack = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }


            //Bugged Function
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.differ.currentList[position]
                viewModel.removeTask(task)
                Snackbar.make(view,"Removed the Task",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.addTask(task)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(touchCallBack).attachToRecyclerView(rv_tasks)

        adapter.setOnClickTaskListener {
            val bundle = Bundle().apply {
                putSerializable("currentTask",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_addFragment,bundle)
        }



        viewModel.timeOfDay.observe(viewLifecycleOwner, Observer { greetingText->
            greeting_text_tv.setText(getString(R.string.greeting_string,greetingText))
        })

        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks->
            if(tasks.isNotEmpty()){
                no_task_added_layout.visibility = View.GONE
                adapter.differ.submitList(tasks)

            }
            else{
                no_task_added_layout.visibility = View.VISIBLE
            }
        })

        viewModel.totalNumberOfTasks.observe(viewLifecycleOwner, Observer {totalTasks->
            number_tasks_tv.setText(getString(R.string.num_tasks_show,totalTasks))
        })
    }

    fun setUpRecyclerView(){
        adapter = TaskAdapter()
        rv_tasks.layoutManager = LinearLayoutManager(requireContext())
        rv_tasks.adapter = adapter
    }
}