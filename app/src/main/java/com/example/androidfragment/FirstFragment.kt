package com.example.androidfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class FirstFragment : Fragment(), Removable{

    private lateinit var db: DBHelper
    private var tasks = arrayListOf<Task>()
    private lateinit var toolbar: Toolbar
    private lateinit var taskET: EditText
    private lateinit var saveBTN: Button
    private lateinit var clearBTN: Button
    private lateinit var recRV: RecyclerView
    private lateinit var adapter: TaskRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        saveBTN.setOnClickListener {
            if (taskET.text.isNotEmpty()) {
                val task =
                    Task(createId(), taskET.text.toString(), false, LocalDate.now().toString())
                db.addTask(task)
                //tasks.add(task)
                updateData()
                taskET.hint = ""
                taskET.text.clear()
            } else {
                taskET.hint = "Поле не может быть пустым"
            }
        }
        clearBTN.setOnClickListener {
            db.removeAll()
            updateData()
        }
        adapter.setOnTasksClickListner(object :
            TaskRecyclerAdapter.OnTasksClickListener {
            override fun onTaskClick(task: Task, position: Int) {
//                val builder = AlertDialog.Builder(
//                    requireActivity()
//                )
//                builder
//                    .setTitle("Внимание!")
//                    .setMessage("Удалить заметку?")
//                    .setPositiveButton("Да") { _, _ ->
//                        db.deleteTask(task)
//                        updateData()
//                    }
//                    .setNegativeButton("Нет", null)
//                    .create()
//                    .show()
            }
        })

    }

    private fun init(view: View) {
        db = DBHelper(this.requireContext())
//        tasks = db.readTasks()
        toolbar = view.findViewById(R.id.toolbar)
        taskET = view.findViewById(R.id.taskET)
        saveBTN = view.findViewById(R.id.btn)
        clearBTN = view.findViewById(R.id.clearDB)
        recRV = view.findViewById(R.id.recRV)
        recRV.layoutManager = LinearLayoutManager(this.requireContext())
//        adapter = TaskRecyclerAdapter(tasks, requireContext())
//        recRV.adapter = adapter
        updateData()
    }

    private fun createId(): Int {
        var id = 1
        var flag = true
        while (flag) {
            flag = false
            for (task in tasks) {
                if (task.id == id) {
                    id++
                    flag = true
                    break
                }
            }
        }
        return id
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateData() {
        tasks = db.readTasks()
        adapter = TaskRecyclerAdapter(tasks, requireContext())
//        adapter.notifyDataSetChanged()
        recRV.adapter = adapter
    }

    override fun remove(task: Task) {
        db.deleteTask(task)
    }
}