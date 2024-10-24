package com.example.mylibrary.screens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.services.FirebaseService

class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para o FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseService.registerUser(email, password) { isSuccess, message ->
                if (isSuccess) {
                    Toast.makeText(this, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show()
                    // Navegar para a tela de login ou outra atividade
                    finish() // Opcional: finalizar a tela de cadastro
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }
}
