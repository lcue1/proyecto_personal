package com.example.tasks.view.animations

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tasks.R
import com.example.tasks.view.RegisterActivity
import de.hdodenhof.circleimageview.CircleImageView

class InitAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_init_app)

        val circleView: View = findViewById(R.id.circleView)
        startBouncingAnimation(circleView)
        navigateToMainScreen()

    }
    private fun startBouncingAnimation(view: View) {
        // Cargar el animador desde XML
        val animator = AnimatorInflater.loadAnimator(this, R.animator.init_app_animator) as ObjectAnimator
        animator.target = view // Asignar la vista como objetivo
        animator.start()       // Iniciar la animación
    }


    private fun navigateToMainScreen() {
        val imageView = findViewById<CircleImageView>(R.id.taskImg)
        val title = findViewById<TextView>(R.id.titlelInit)

        // Mostrar la imagen después de una animación inicial
        Handler(Looper.getMainLooper()).postDelayed({
            imageView.visibility = View.VISIBLE
            title.visibility=View.VISIBLE
        }, 2000) // Retraso para mostrar la imagen (2 segundos)

        // Navegar a la siguiente pantalla después de que la imagen esté visible por más tiempo
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }, 5000) // Total 5 segundos antes de cambiar de actividad

    }

}