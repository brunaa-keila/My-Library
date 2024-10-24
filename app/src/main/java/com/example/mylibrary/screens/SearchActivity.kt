package com.example.mylibrary.screens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.adapters.BookAdapter
import com.example.mylibrary.models.Book
import com.example.mylibrary.services.FirebaseService

class SearchActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var listView: ListView
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para o FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        listView = findViewById(R.id.listView)

        searchButton.setOnClickListener {
            searchBooks()
        }
    }

    private fun searchBooks() {
        val query = searchEditText.text.toString()

        if (query.isNotEmpty()) {
            firebaseService.searchBooks(query) { books ->
                // Aqui você pode configurar um Adapter para exibir os resultados da pesquisa
                val adapter = BookAdapter(this, books) // Crie um adapter para sua ListView
                listView.adapter = adapter
            }
        } else {
            Toast.makeText(this, "Por favor, insira um termo de busca.", Toast.LENGTH_SHORT).show()
        }
    }
}
