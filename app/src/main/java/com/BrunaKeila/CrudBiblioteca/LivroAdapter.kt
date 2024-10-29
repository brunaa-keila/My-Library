package com.BrunaKeila.CrudBiblioteca

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LivroAdapter (private val livros: List<Livro>):
    RecyclerView.Adapter<LivroAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        Log.v("LOG", "onCreate")
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val vh = VH(v)

        vh.itemView.setOnClickListener{
            val livro= livros[vh.adapterPosition]
            val itLivro = Intent(parent.context, UpdateActivity::class.java)
            itLivro.putExtra("livro",livro)
            parent.context.startActivity(itLivro)
        }

        vh.editarPag.setOnClickListener{
            val livro= livros[vh.adapterPosition]
            val itLivro = Intent(parent.context, UpdateActivityPag::class.java)
            itLivro.putExtra("livro",livro)
            parent.context.startActivity(itLivro)
        }
        return vh
    }

    override fun getItemCount(): Int {
        return livros.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.v("LOG", "ViewHolder")
        var livro = livros[position]
        holder.titulo.text =livro.titulo
        holder.paginas.text=livro.paginas.toString()
        holder.paginasLidas.text = livro.paginasLidas.toString()
        holder.tipo.text=livro.tipo
        holder.autor.text=livro.autor
        holder.lido.isChecked = livro.paginas == livro.paginasLidas
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        var titulo = view.findViewById<TextView>(R.id.txtProduto)
        var paginas = view.findViewById<TextView>(R.id.paginas)
        var paginasLidas = view.findViewById<TextView>(R.id.paginasLidas)
        var tipo = view.findViewById<TextView>(R.id.genero)
        var autor = view.findViewById<TextView>(R.id.autor)
        var lido = view.findViewById<CheckBox>(R.id.lido)
        var editarPag = view.findViewById<FloatingActionButton>(R.id.editarPagina)

        init {
            titulo = view.findViewById(R.id.txtProduto)
            paginas = view.findViewById(R.id.paginas)
            paginasLidas = view.findViewById(R.id.paginasLidas)
            tipo= view.findViewById(R.id.genero)
            autor= view.findViewById(R.id.autor)
            lido= view.findViewById(R.id.lido)
            editarPag= view.findViewById(R.id.editarPagina)
        }
    }
}