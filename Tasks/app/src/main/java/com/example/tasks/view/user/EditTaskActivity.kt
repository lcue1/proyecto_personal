package com.example.tasks.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasks.R
import com.example.tasks.databinding.ActivityAddTaskBinding
import com.example.tasks.databinding.ActivityEditTaskBinding
import com.example.tasks.model.Task
import com.example.tasks.model.TaskDatabaseHelper
import com.example.tasks.utils.returnToUserActivity
import com.example.tasks.utils.validateEditText

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditTaskBinding
    private lateinit var taskToEdit:Task
    private  lateinit var taskDBHelper:TaskDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        initAtributes()
        loadDataInViews()

        binding.cancelBtn.setOnClickListener {//Return to before activity
            returnToUserActivity(RESULT_CANCELED, "No se agrego ninguna tarea")
        }
        binding.updateBtn.setOnClickListener {
            updateTaskInDatabase()
        }
        binding.deleteBtn.setOnClickListener {
            taskDBHelper.deleteTask(taskToEdit.id)
            returnToUserActivity(RESULT_OK,"Tarea eliminada.")
        }

    }

    private fun updateTaskInDatabase() {
        val validationEdit= validateEditText(this, listOf(binding.title,binding.description))
       if (validationEdit){
           val time = String.format("%02d:%02d",binding.time.hour,binding.time.minute)
           taskDBHelper.updateTask(
               taskToEdit.id,
               binding.title.text.toString(),
               binding.description.text.toString(),
               time
           )
           returnToUserActivity(RESULT_OK,"Tarea actualizada.")
       }
    }

    private fun loadDataInViews() {
        binding.idTask.text=binding.idTask.text.toString()+taskToEdit.id.toString()
        binding.title.setText(taskToEdit.title)
        binding.description.setText(taskToEdit.description)
        Log.d("hour",taskToEdit.time.toString())

        val index=taskToEdit.time.indexOf(":")
        val hour = taskToEdit.time.substring(0,index)
        val minute = taskToEdit.time.substring(index+1)
        binding.time.hour = hour.toInt()
        binding.time.minute = minute.toInt()

    }

    private fun initAtributes() {
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskToEdit= intent.getParcelableExtra("extraData")!!
        taskDBHelper = TaskDatabaseHelper.createDatabase(this)!!


    }
}