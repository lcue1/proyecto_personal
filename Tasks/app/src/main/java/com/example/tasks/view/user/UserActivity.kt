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
import com.example.tasks.model.Task
import com.example.tasks.model.User

class UserActivity : AppCompatActivity() {
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val resultValue = data?.getStringExtra("resultKey")
            // Maneja el resultado
            println("Resultado recibido: $resultValue")
        }
    }//  Launch second activity and get result from it
    //Atributos
    private lateinit var binding:ActivityUserBinding
    private  var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        initAtributes()
        Log.d("user", user?.name.toString())
        loadDataInUI()

        //Recycleview
        val recyclerView = findViewById<RecyclerView>(R.id.taskRecycle)

       binding.addTaskBtn.setOnClickListener { insertTask() }




    }

    private fun insertTask() {
        val intent = Intent(this,AddTaskActivity::class.java)
        intent.putExtra("user", user?.name)
        resultLauncher.launch(intent)
        // Configurar el RecyclerView
        //  recyclerView.layoutManager = LinearLayoutManager(this)
        //    recyclerView.adapter = TaskAdapter(itemList)
    }

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
    }

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

    }




}