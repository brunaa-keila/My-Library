package com.example.mylibrary.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.adapters.BookAdapter
import com.example.mylibrary.models.Book
import com.example.mylibrary.services.FirebaseService

class HomeActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var addBookButton: Button
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para o FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        listView = findViewById(R.id.listView)
        addBookButton = findViewById(R.id.addBookButton)

        addBookButton.setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }

        fetchBooks()
    }

    private fun fetchBooks() {
        firebaseService.fetchBooks { books ->
            // Aqui você pode configurar um Adapter para exibir os livros na ListView
            val adapter = BookAdapter(this, books) // Crie um adapter para sua ListView
            listView.adapter = adapter
        }
    }
}
