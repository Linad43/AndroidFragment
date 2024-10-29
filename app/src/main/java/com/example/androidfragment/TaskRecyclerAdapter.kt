package com.example.androidfragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskRecyclerAdapter(
    private val tasks: ArrayList<Task>,
    private val context: Context
) : RecyclerView.Adapter<TaskRecyclerAdapter.TasksViewHolder>() {
    private var onTasksClickListener: OnTasksClickListener? = null
    private var db = DBHelper(context)
    interface OnTasksClickListener {
        fun onTaskClick(task: Task, position: Int)
    }

    class TasksViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val textTV: TextView = itemView.findViewById(R.id.textTV)
        val checkBoxCB: CheckBox = itemView.findViewById(R.id.checkBoxCB)
        val dataCreateTV: TextView = itemView.findViewById(R.id.dataCreateTV)
//        val dataAcceptTV = itemView.findViewById<TextView>(R.id.dataAcceptTV)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TasksViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_task_item, parent, false)
        return TasksViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = tasks[position]
        holder.textTV.text = "${task.id}. ${task.text}"
        holder.checkBoxCB.isChecked = task.flag
        holder.dataCreateTV.text = task.dataCreate
        holder.checkBoxCB.setOnCheckedChangeListener { compoundButton, b ->
            task.flag = b
            db.updateTask(task)
        }
        holder.itemView.setOnClickListener {
            if (onTasksClickListener != null) {
                onTasksClickListener!!.onTaskClick(task, position)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size
    fun setOnTasksClickListner(onTasksClickListener: OnTasksClickListener) {
        this.onTasksClickListener = onTasksClickListener
    }
}
