package com.BrunaKeila.CrudBiblioteca

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class LivroDao (context: Context) {
    var banco = DbHelper(context)

    //inserir dados no banco
    fun insert(livro:Livro): String {
        val db = banco.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(LIVRO_TITULO, livro.titulo)
        contentValues.put(LIVRO_PAGINAS, livro.paginas)
        contentValues.put(LIVRO_PAGLIDAS, livro.paginasLidas)
        contentValues.put(LIVRO_TIPO, livro.tipo)
        contentValues.put(LIVRO_AUTOR, livro.autor)

        var resp_id = db.insert(TABLE_LIVROS, null, contentValues)
        val msg = if(resp_id!=-1L){
            "Adicionado com sucesso"
        }else{
            "Erro ao adicionar"
        }
        db.close()
        return msg
    }

    fun buscaLivrosLidos(): List<Livro>{
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABLE_LIVROS + " WHERE PAGINAS = PAGINAS_LIDAS"
        val cursor = db.rawQuery(sql, null)
        val livros = ArrayList<Livro>()
        while(cursor.moveToNext()){
            val livro = livroFromCursor(cursor)
            livros.add(livro)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${livros.size}")
        return livros
    }

    fun buscaLivrosNaoLidos(): List<Livro>{
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABLE_LIVROS + " WHERE NOT(PAGINAS = PAGINAS_LIDAS)"
        val cursor = db.rawQuery(sql, null)
        val livros = ArrayList<Livro>()
        while(cursor.moveToNext()){
            val livro = livroFromCursor(cursor)
            livros.add(livro)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${livros.size}")
        return livros
    }

    //atualizar linha - novos valores nos campos
    fun update (livro: Livro): Boolean{
        val db = banco.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(LIVRO_TITULO, livro.titulo)
        contentValues.put(LIVRO_PAGINAS, livro.paginas)
        contentValues.put(LIVRO_PAGLIDAS, livro.paginasLidas)
        contentValues.put(LIVRO_TIPO, livro.tipo)
        contentValues.put(LIVRO_AUTOR, livro.autor)

        db.updateWithOnConflict(TABLE_LIVROS, contentValues, "ID =?", arrayOf(livro.id.toString()), SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
        return true
    }

    //excluir linha no bd
    fun remove(livro: Livro): Int {
        val db = banco.writableDatabase
        return db.delete(TABLE_LIVROS,"ID =?", arrayOf(livro.id.toString()))
    }

    //getter retorna um Cursor com o conjunto de dados
    fun getAll() : ArrayList<Livro>{
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABLE_LIVROS
        val cursor = db.rawQuery(sql, null)
        val livros = ArrayList<Livro>()
        while(cursor.moveToNext()){
            val livro = livroFromCursor(cursor)
            livros.add(livro)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${livros.size}")
        return livros
    }

    private fun livroFromCursor(cursor: Cursor): Livro{
        val id = cursor.getInt(cursor.getColumnIndex(LIVRO_ID))
        val titulo = cursor.getString(cursor.getColumnIndex(LIVRO_TITULO))
        val paginas = cursor.getInt(cursor.getColumnIndex(LIVRO_PAGINAS))
        val pagLidas = cursor.getInt(cursor.getColumnIndex(LIVRO_PAGLIDAS))
        val tipo = cursor.getString(cursor.getColumnIndex(LIVRO_TIPO))
        val autor = cursor.getString(cursor.getColumnIndex(LIVRO_AUTOR))
        return Livro(id,titulo,paginas,pagLidas, tipo, autor)
    }
}