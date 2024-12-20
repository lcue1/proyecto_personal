package com.example.tasks.view.user

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.tasks.R
import com.example.tasks.databinding.ActivityUserBinding
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
        loadDataInUI()

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