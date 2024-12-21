package com.example.tasks.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasks.R

class AddTaskActivity : AppCompatActivity() {
    private lateinit var userName:String
    private lateinit var resultIntent:Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)

        initAtributs()
        Log.d("extra",userName.toString())
        returnToUserActivity()

    }

    private fun returnToUserActivity() {
        resultIntent = Intent()
        resultIntent.putExtra("resultKey", "Este es el resultado")
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun initAtributs() {
        userName= intent.getStringExtra("user")!!
    }
}