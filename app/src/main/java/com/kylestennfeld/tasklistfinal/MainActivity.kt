package com.kylestennfeld.tasklistfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.kylestennfeld.tasklistfinal.Adapter.RecyclerAdapter
import com.kylestennfeld.tasklistfinal.Model.TaskViewModel
import com.kylestennfeld.tasklistfinal.Utils.DatabaseHandler
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), DialogListener {
    var db = DatabaseHandler(this)
    lateinit var adapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val dialog = AddTaskFragment(this)
            dialog.show(supportFragmentManager, "addTaskDialog")
        }


        val swipeAction = object: SwipeHandler() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(direction == ItemTouchHelper.LEFT) {
                    val position = viewHolder.absoluteAdapterPosition
                    val item = adapter.taskList[position]
                    db.deleteData(item)
                    adapter.taskList = db.viewData()
                    adapter.notifyDataSetChanged()
                }
            }
        }


        val touchHelper = ItemTouchHelper(swipeAction)
        touchHelper.attachToRecyclerView(recyclerview)

        adapter = RecyclerAdapter(this, db.viewData())
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }




    override fun addDocumentHandler(task: TaskViewModel) {
        db.addTask(task)
        adapter.taskList = db.viewData()
        adapter.notifyDataSetChanged()
    }


}