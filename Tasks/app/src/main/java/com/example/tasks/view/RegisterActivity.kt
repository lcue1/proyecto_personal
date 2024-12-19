package com.example.tasks.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tasks.R
import com.example.tasks.databinding.ActivityRegisterBinding
import com.example.tasks.model.User
import com.example.tasks.utils.validateEditText
import com.example.tasks.view.user.UserActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        initAtributes()//Init atributtes of this class
        binding.image.setOnClickListener{selectImage()}//Load image
        binding.registerBtn.setOnClickListener {register()}//validate and register user in bd

    }

    private fun register() {
        val validation=validateEditText(this, arrayListOf(binding.name,binding.password))
        if(validation){
            val user = User(1,binding.name.text.toString(), binding.password.toString(), imageUri.toString())
            val intent =Intent(this,UserActivity::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
        }

    }
    //Load an image
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*" // Selección de cualquier tipo de imagen
        register.launch(intent)
    }
    //Logica load image
    var imageUri: Uri?=null
    private val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            binding.image.setImageURI(imageUri) // Muestra la imagen en el ImageView
        }
    }
    private fun initAtributes(){

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}