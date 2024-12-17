package com.example.tasks.view

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tasks.R
import com.example.tasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

                binding.registerBtn.setOnClickListener {
                val validation=validateEditText(arrayListOf(binding.name, binding.dni,binding.password))
              if(validation){

              }

        }

    }

    private fun validateEditText(edits:List<EditText>):Boolean {
        val wrongEdit = edits.filter { it.text.toString().isEmpty() }
        wrongEdit.forEach {
            it.setHintTextColor(ContextCompat.getColor(this,R.color.red))
            it.error = "Cammpo requerido"
        }
        return wrongEdit.isEmpty()
    }
}