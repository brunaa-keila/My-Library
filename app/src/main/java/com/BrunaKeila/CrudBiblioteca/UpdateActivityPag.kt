package com.BrunaKeila.CrudBiblioteca

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpdateActivityPag : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar)

        val titulo = findViewById<EditText>(R.id.titulo2)
        val paginas = findViewById<EditText>(R.id.paginas2)
        val paginasLidas = findViewById<EditText>(R.id.paginasLidas)
        val tipo = findViewById<EditText>(R.id.tipo2)
        val autor = findViewById<EditText>(R.id.autor2)
        val FABSave = findViewById<FloatingActionButton>(R.id.FABSave)
        val FABBack = findViewById<FloatingActionButton>(R.id.FABBack)
        val livro = intent.getParcelableExtra<Livro>("livro")

        val FABRemove = findViewById<FloatingActionButton>(R.id.FABRemove)
        FABRemove.setVisibility(View.GONE)

        titulo.setText(livro?.titulo.toString())
        paginas.setText(livro?.paginas.toString())
        tipo.setText(livro?.tipo.toString())
        autor.setText(livro?.autor.toString())
        paginasLidas.setText(livro?.paginasLidas.toString())

        titulo.setEnabled(false)
        paginas.setEnabled(false)
        tipo.setEnabled(false)
        autor.setEnabled(false)

        FABSave.setOnClickListener {
            var livroUP = Livro(livro?.id, titulo.text.toString(),paginas.text.toString().toInt(), paginasLidas.text.toString().toInt(), tipo.text.toString(), autor.text.toString())
            if(livroUP.paginas < livroUP.paginasLidas){
                Toast.makeText(applicationContext, "Total de páginas não pode ser maior que páginas lidas.", Toast.LENGTH_LONG).show()
            }else{
                var livroDao = LivroDao(this)
                livroDao.update(livroUP)
                onBackPressed()
            }
        }

        FABBack.setOnClickListener {
            onBackPressed()
        }
    }
}


