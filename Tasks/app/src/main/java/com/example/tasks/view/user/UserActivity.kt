package com.example.tasks.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasks.R
import com.example.tasks.databinding.ActivityRegisterBinding
import com.example.tasks.databinding.ActivityUserBinding
import com.example.tasks.model.TaskDatabaseHelper
import com.example.tasks.model.User

class UserActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUserBinding
    private  var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        initAtributes()
        Log.d("user", user?.name.toString())
        loadInterface()

    }

    private fun loadInterface() {
        binding.imageUser.setImageURI(user?.image?.toUri())
        binding.userName.text = user?.name ?: ""
    }

    private fun initAtributes() {
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
         user = intent.getParcelableExtra("user")

    }
}