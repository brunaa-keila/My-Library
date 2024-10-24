package com.example.mylibrary.screens

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mylibrary.R
import com.example.mylibrary.models.Book
import com.example.mylibrary.services.FirebaseService

class BookDetailActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var summaryTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var deleteButton: Button
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para o FirebaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        titleTextView = findViewById(R.id.titleTextView)
        authorTextView = findViewById(R.id.authorTextView)
        summaryTextView = findViewById(R.id.summaryTextView)
        imageView = findViewById(R.id.imageView)
        deleteButton = findViewById(R.id.deleteButton)

        val book = intent.getParcelableExtra<Book>("BOOK") ?: return

        titleTextView.text = book.title
        authorTextView.text = book.author
        summaryTextView.text = book.summary
        Glide.with(this).load(book.imageUrl).into(imageView)

        deleteButton.setOnClickListener {
            // Chama o método deleteBook com o callback
            firebaseService.deleteBook(book.id) { isSuccess, message ->
                if (isSuccess) {
                    Toast.makeText(this, "Livro deletado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Retorna à tela anterior após deletar
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show() // Exibe mensagem de erro
                }
            }
        }
    }
}
