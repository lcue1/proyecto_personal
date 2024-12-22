package com.example.tasks.view.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.databinding.ActivityUserBinding
import com.example.tasks.model.TaskDatabaseHelper
import com.example.tasks.model.User

class UserActivity : AppCompatActivity() {
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_CANCELED) {
            val data = result.data
            val resultValue = data?.getStringExtra("userAction")
            Toast.makeText(this,resultValue,Toast.LENGTH_SHORT).show()
        }else  if (result.resultCode == RESULT_OK) {
            val data = result.data
            val resultValue = data?.getStringExtra("userAction")
            val tasks=taskDB.getAllTasks()
            binding.taskRecycle.layoutManager = LinearLayoutManager(this)
            binding.taskRecycle.adapter=TaskAdapter(tasks)
            Toast.makeText(this,resultValue,Toast.LENGTH_SHORT).show()
            //falta recargar  reccycle view
        }
    }//  Launch second activity and get result from it
    //Atributos
    private lateinit var binding:ActivityUserBinding
    private lateinit var taskDB:TaskDatabaseHelper
    private  var user: User? = null
    private lateinit var taskAdapter:TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        initAtributes()
        loadDataInUI()

        //Recycleview
        val recyclerView = findViewById<RecyclerView>(R.id.taskRecycle)

       binding.addTaskBtn.setOnClickListener { insertTask() }


    }

    private fun insertTask() {
        val intent = Intent(this,AddTaskActivity::class.java)
        intent.putExtra("user", user)
        resultLauncher.launch(intent)
    }

    //Load image and  user name
    private fun loadDataInUI() {
        try {
            val uri = user?.image?.toUri()
            if (uri != null && isUriAccessible(uri)) {
                binding.imageUser.setImageURI(uri)
            }
        }catch (e:Exception){
            Toast.makeText(this,"No se puede cargar la imagen",Toast.LENGTH_LONG).toString()
        }
        binding.userName.text = user?.name ?: ""
        val tasks=taskDB.getAllTasks()
        binding.taskRecycle.layoutManager = LinearLayoutManager(this)
        binding.taskRecycle.adapter=TaskAdapter(tasks)
    }
    // Permision  uri access
    private fun isUriAccessible(uri: Uri): Boolean {
        return try {
            contentResolver.openInputStream(uri)?.close()
            true
        } catch (e: Exception) {
            Log.e("UserActivity", "Error al acceder al URI: ${e.message}")
            false
        }
    }

    private fun initAtributes() {
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
         user = intent.getParcelableExtra("user")
        taskDB = TaskDatabaseHelper.createDatabase(this)!!


    }


}