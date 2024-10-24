package com.example.mylibrary.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mylibrary.R
import com.example.mylibrary.models.Book
import com.example.mylibrary.services.FirebaseService

class AddBookActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var summaryEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var addBookButton: Button
    private val firebaseService by lazy { FirebaseService(this) } // Passa o contexto para o FirebaseService
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        titleEditText = findViewById(R.id.titleEditText)
        authorEditText = findViewById(R.id.authorEditText)
        summaryEditText = findViewById(R.id.summaryEditText)
        imageView = findViewById(R.id.imageView)
        addBookButton = findViewById(R.id.addBookButton)

        imageView.setOnClickListener {
            selectImage()
        }

        addBookButton.setOnClickListener {
            addBook()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun addBook() {
        val title = titleEditText.text.toString()
        val author = authorEditText.text.toString()
        val summary = summaryEditText.text.toString()

        if (title.isNotEmpty() && author.isNotEmpty() && summary.isNotEmpty() && imageUri != null) {
            val book = Book(title, author, summary, imageUri.toString())
            firebaseService.addBook(book) {
                Toast.makeText(this, "Livro adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PICK_IMAGE = 1
    }
}
