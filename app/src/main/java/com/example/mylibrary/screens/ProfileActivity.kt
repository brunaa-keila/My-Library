package com.example.mylibrary.screens

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.services.FirebaseService

class ProfileActivity : AppCompatActivity() {
    private lateinit var emailTextView: TextView
    private lateinit var logoutButton: Button
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para o FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        emailTextView = findViewById(R.id.emailTextView)
        logoutButton = findViewById(R.id.logoutButton)

        emailTextView.text = firebaseService.getCurrentUserEmail() // Obtém o email do usuário

        logoutButton.setOnClickListener {
            firebaseService.logoutUser() // Faz logout do usuário
            finish() // Finaliza a atividade atual
        }
    }
}
