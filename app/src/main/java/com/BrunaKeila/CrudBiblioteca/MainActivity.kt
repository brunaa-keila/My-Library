package com.BrunaKeila.CrudBiblioteca

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var listaLivros = mutableListOf<Livro>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateAdapter()
        initRecyclerView()

        var btnAdd = findViewById<FloatingActionButton>(R.id.btnAdd)
        var lidos = findViewById<Button>(R.id.lidos)
        var naolidos = findViewById<Button>(R.id.naolidos)

        btnAdd.setOnClickListener {
            val itLivro = Intent(this, SaveActivity::class.java)
            startActivity(itLivro)
        }

        lidos.setOnClickListener{
            verificaListagem(true)
        }

        naolidos.setOnClickListener{
            verificaListagem(false)
        }

    }

    fun verificaListagem(value : Boolean){
        val i = Intent(this, ListagemActivity::class.java)
        i.putExtra("key", value)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
        initRecyclerView()
    }

    private fun updateAdapter(){
        var rvDados = findViewById<RecyclerView>(R.id.rvDados)
        var txtMsg = findViewById<TextView>(R.id.txtMsg)
        val livroDao = LivroDao(this)
        listaLivros.clear() //todo
        listaLivros = livroDao.getAll()
        if (listaLivros.isEmpty()) {
            rvDados.setVisibility(View.GONE)
            txtMsg.setVisibility(View.VISIBLE)
            txtMsg.setText("Sem dados para exibir")
        }
        else {
            rvDados.setVisibility(View.VISIBLE)
            txtMsg.setVisibility(View.GONE)
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