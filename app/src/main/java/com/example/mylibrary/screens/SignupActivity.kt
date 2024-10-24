package com.example.mylibrary.screens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.services.FirebaseService

class SignupActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: Button
    private val firebaseService by lazy { FirebaseService(this) } // Passando o contexto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signupButton = findViewById(R.id.signupButton)

        signupButton.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseService.createUser(email, password) { isSuccess, message ->
                if (isSuccess) {
                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela de login
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }
}
