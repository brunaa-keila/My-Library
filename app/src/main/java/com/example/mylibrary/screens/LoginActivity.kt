package com.example.mylibrary.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.services.FirebaseService

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para a FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerTextView = findViewById(R.id.registerTextView)

        loginButton.setOnClickListener {
            loginUser()
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java)) // Verifique o nome da sua Activity de registro
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseService.loginUser(email, password) { isSuccess, message ->
                if (isSuccess) {
                    startActivity(Intent(this, NavigationActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, message ?: "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }
}
