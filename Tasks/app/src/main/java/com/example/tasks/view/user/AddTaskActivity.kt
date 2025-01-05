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
import com.example.tasks.utils.returnToUserActivity
import com.example.tasks.utils.validateEditText
import com.example.tasks.view.notifications.createNotification
import java.util.Calendar

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
        val validationEdit = validateEditText(this, listOf(binding.title,binding.description,))
        if (validationEdit){
            val hourPicker= binding.time.hour
            val minutePicker= binding.time.minute

            val time = String.format("%02d:%02d",hourPicker, minutePicker)
            val saveTask = taskDB.insertTask(user.id!!.toInt(),
                binding.title.text.toString(),
                binding.description.text.toString(),
                time)
            if(saveTask>0){
                createNotification(this,calulateMiliSeconds(hourPicker,minutePicker))
                returnToUserActivity(RESULT_OK, "Tarea agregada")
            }
        }
    }

    private fun calulateMiliSeconds(hour: Int, minute: Int): Long {
        var miliSeconds = 0L
        val calendar = Calendar.getInstance()
        val actualHour = calendar.get(Calendar.HOUR_OF_DAY)
        val actualMinute = calendar.get(Calendar.MINUTE)
        val pickerMiliseconds = getMillisecondsFromTimePicker(hour,minute)

        val actualTimeMiliseconds = getMillisecondsFromTimePicker(actualHour,actualMinute)
        miliSeconds = pickerMiliseconds-actualTimeMiliseconds

        return  miliSeconds
    }
    private fun getMillisecondsFromTimePicker(hour: Int, minute: Int): Long {
        // Usa Calendar para calcular los milisegundos
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }


    private fun initAtributs() {
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user= intent.getParcelableExtra("extraData")!!
         taskDB = TaskDatabaseHelper.createDatabase(this)!!
        binding.time.setIs24HourView(true)
    }
}