package com.example.mylibrary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.screens.LoginActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o Firebase
        FirebaseApp.initializeApp(this)

        // Define o layout XML da MainActivity
        setContentView(R.layout.activity_main)

        // Adiciona um pequeno atraso para evitar que a tela feche muito rápido
        navigateToLoginActivityWithDelay()
    }

    private fun navigateToLoginActivityWithDelay() {
        // Usa um Handler para adicionar um pequeno atraso antes de iniciar a LoginActivity
        android.os.Handler().postDelayed({
            // Inicia a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Agora finalizamos a MainActivity após a transição
        }, 1500) // Atraso de 1.5 segundos (1500 milissegundos)
    }
}
