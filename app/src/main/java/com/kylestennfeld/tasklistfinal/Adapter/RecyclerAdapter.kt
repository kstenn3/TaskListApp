package com.kylestennfeld.tasklistfinal.Adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kylestennfeld.tasklistfinal.Model.TaskViewModel
import com.kylestennfeld.tasklistfinal.R


class RecyclerAdapter(private val context: Context, var taskList: ArrayList<TaskViewModel>)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var checkBoxState = SparseBooleanArray()

    /* create new card views for new tasks */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).
        inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    /* bind new tasks to task list */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /* find if checkbox is checked or not */
        holder.checkBox.isChecked = checkBoxState.get(position, false)
        val list = taskList[position]
        holder.textView.text = list.task
        holder.checkBox.text = ""

    }

    /* isChecked has boolean data type. Conver Int to boolean */
    private fun checkFlag(x: Int) : Boolean {
        return x != 0
    }

    /* return sum of items in taskList */
    override fun getItemCount(): Int {
        return taskList.size
    }


    /* holds items/views for each card */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)
        val textView: TextView = view.findViewById(R.id.taskText)

        /* keep check box checked when scrolling */
        init {
            checkBox.setOnClickListener {
                if (!checkBoxState.get(absoluteAdapterPosition, false)) {
                    checkBox.isChecked = true
                    checkBoxState.put(absoluteAdapterPosition, true)
                } else {
                    checkBox.isChecked = false
                    checkBoxState.put(absoluteAdapterPosition, false)
                }
            }
        }
    }
}