package com.kylestennfeld.tasklistfinal

import com.kylestennfeld.tasklistfinal.Model.TaskViewModel

interface DialogListener {
    fun addDocumentHandler(task: TaskViewModel)
}