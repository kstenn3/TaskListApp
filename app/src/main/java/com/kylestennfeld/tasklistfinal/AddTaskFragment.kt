package com.kylestennfeld.tasklistfinal

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.kylestennfeld.tasklistfinal.Model.TaskViewModel


class AddTaskFragment(val taskString: DialogListener): DialogFragment() {
    lateinit var taskText: String
    lateinit var task: TaskViewModel
    private lateinit var addButton: Button
    private lateinit var dismissButton: Button
    private lateinit var editText: EditText
    private lateinit var rootView: View



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.add_task_layout, container, false)
        dismissButton = rootView.findViewById<Button>(R.id.buttonDismiss)
        addButton = rootView.findViewById<Button>(R.id.buttonAdd)
        editText = rootView.findViewById<EditText>(R.id.addTask)

        dismissButton.setOnClickListener { dismiss() }

        addButton.setOnClickListener {
            taskText = editText.text.toString()
            /* check task has been entered */
            if(taskText.isNotEmpty()) {
                val task = TaskViewModel(0, taskText, 0)
                taskString.addDocumentHandler(task)
                dismiss()
            } else {
                Toast.makeText(context, "Enter Task", Toast.LENGTH_LONG).show()
            }
        }
        return rootView
    }
}