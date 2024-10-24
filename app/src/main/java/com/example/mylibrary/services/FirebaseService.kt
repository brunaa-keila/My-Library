package com.example.mylibrary.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mylibrary.models.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseService(private val context: Context) { // Adicione o Context como parâmetro no construtor
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance() // Inicializa o Firestore

    fun loginUser(email: String, password: String, onComplete: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete()
                } else {
                    // Aqui você pode tratar erros de login, por exemplo
                    Toast.makeText(context, "Erro ao fazer login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun registerUser(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Cadastro bem-sucedido!")
                } else {
                    onComplete(false, task.exception?.message ?: "Erro desconhecido")
                }
            }
    }

    fun addBook(book: Book, onComplete: () -> Unit) {
        db.collection("books") // Altere o nome da coleção conforme necessário
            .add(book)
            .addOnSuccessListener {
                onComplete()
            }
            .addOnFailureListener { e ->
                // Exiba uma mensagem de erro
                Toast.makeText(context, "Erro ao adicionar livro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun deleteBook(bookId: String, onComplete: (Boolean, String) -> Unit) {
        db.collection("books").document(bookId) // Obtém o documento do livro a ser excluído
            .delete()
            .addOnSuccessListener {
                onComplete(true, "Livro deletado com sucesso!")
            }
            .addOnFailureListener { e ->
                // Exiba uma mensagem de erro
                Toast.makeText(context, "Erro ao deletar livro: ${e.message}", Toast.LENGTH_SHORT).show()
                onComplete(false, e.message ?: "Erro desconhecido")
            }
    }

    fun fetchBooks(onComplete: (List<Book>) -> Unit) {
        db.collection("books") // Altere o nome da coleção conforme necessário
            .get()
            .addOnSuccessListener { documents ->
                val books = mutableListOf<Book>()
                for (document in documents) {
                    val book = document.toObject(Book::class.java) // Converte o documento em um objeto Book
                    books.add(book)
                }
                onComplete(books) // Retorna a lista de livros para o callback
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao buscar livros: ${e.message}", Toast.LENGTH_SHORT).show()
                onComplete(emptyList()) // Retorna uma lista vazia em caso de erro
            }
    }
    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email // Retorna o email do usuário atual
    }
    fun logoutUser() {
        auth.signOut() // Faz logout do usuário
        Toast.makeText(context, "Logout realizado com sucesso", Toast.LENGTH_SHORT).show()
    }
    fun searchBooks(query: String, onComplete: (List<Book>) -> Unit) {
        db.collection("books") // Altere o nome da coleção conforme necessário
            .orderBy("title") // Altere "title" para o campo que você deseja buscar
            .startAt(query)
            .endAt(query + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                val books = documents.mapNotNull { document ->
                    document.toObject(Book::class.java).apply {
                        id = document.id // Atribui o ID do documento ao objeto Book
                    }
                }
                onComplete(books)
            }
            .addOnFailureListener { e ->
                // Exiba uma mensagem de erro
                Toast.makeText(context, "Erro ao buscar livros: ${e.message}", Toast.LENGTH_SHORT).show()
                onComplete(emptyList()) // Retorna uma lista vazia em caso de erro
            }
    }
    fun createUser(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Usuário criado com sucesso.")
                } else {
                    callback(false, task.exception?.message ?: "Erro ao criar usuário.")
                }
            }
    }
    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    callback(true, null)
                } else {
                    // Falha no login
                    callback(false, task.exception?.message)
                }
            }
    }

}



