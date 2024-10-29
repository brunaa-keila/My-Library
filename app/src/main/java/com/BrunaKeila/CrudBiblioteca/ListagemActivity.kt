package com.BrunaKeila.CrudBiblioteca

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListagemActivity: AppCompatActivity() {
    private var listaLivros = mutableListOf<Livro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.listagem)

            val extras = intent.extras
            var titulo = findViewById<TextView>(R.id.titulo)

            var returnButton = findViewById<FloatingActionButton>(R.id.returnButton)
            returnButton.setOnClickListener {
                onBackPressed()
            }

            if (extras != null) {
                val value = extras.getBoolean("key")
                //The key argument here must match that used in the other activity
                var livroDao = LivroDao(this)
                if (value) {
                    listaLivros = livroDao.buscaLivrosLidos() as MutableList<Livro>
                    titulo.text = "Livros lidos"
                    return
                }
                listaLivros = livroDao.buscaLivrosNaoLidos() as MutableList<Livro>
                titulo.text = "Livros não lidos"
            }
        }catch (erro : Exception){
            Log.i("ERRO", "$erro")
        }
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
        initRecyclerView()
    }

    private fun updateAdapter(){
        var rvDados = findViewById<RecyclerView>(R.id.rvDados)
        if (listaLivros.isEmpty()) {
            rvDados.setVisibility(View.GONE)
        }
        else {
            rvDados.setVisibility(View.VISIBLE)
        }
        rvDados.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView(){
        val rvDados = findViewById<RecyclerView>(R.id.rvDados)
        Log.v("LOG", "Inicia RecyclerView")
        val adapter2 = LivroAdapter(listaLivros)
        rvDados.adapter = adapter2

        val layoutManager= LinearLayoutManager(this)
        rvDados.layoutManager=layoutManager
    }


}