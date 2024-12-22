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
import com.example.tasks.model.TaskDatabaseHelper
import com.example.tasks.model.User
import com.example.tasks.utils.validateEditText

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddTaskBinding
    private lateinit var user:User
    private lateinit var resultIntent:Intent
    private lateinit var taskDB:TaskDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)


        initAtributs()


        binding.cancelBtn.setOnClickListener {//Return to before activity
            returnToUserActivity(RESULT_CANCELED, "No se agrego ninguna tarea") }

        binding.addTaskBtn.setOnClickListener {
            addTaskInDB()
        }


    }

    private fun addTaskInDB() {
        val validation = validateEditText(this, listOf(binding.title,binding.description,))
        if (validation){
            val time = String.format("%02d:%02d",binding.time.hour,binding.time.minute)
            val saveTask = taskDB.insertTask(user.id!!.toInt(),
                binding.title.text.toString(),
                binding.description.text.toString(),
                time)

            returnToUserActivity(RESULT_OK, "Tarea agregada")
        }
    }

    private fun returnToUserActivity(RESULT_CODE:Int, value:String) {//go before activity
        resultIntent = Intent()
        resultIntent.putExtra("userAction", value)
        setResult(RESULT_CODE, resultIntent)
        finish()
    }

    private fun initAtributs() {
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user= intent.getParcelableExtra("user")!!
         taskDB = TaskDatabaseHelper.createDatabase(this)!!

    }
}