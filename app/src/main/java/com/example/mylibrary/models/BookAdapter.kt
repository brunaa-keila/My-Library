package com.example.mylibrary.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mylibrary.R
import com.example.mylibrary.models.Book

class BookAdapter(context: Context, private val books: List<Book>) : ArrayAdapter<Book>(context, 0, books) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val book = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)

        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val authorTextView: TextView = view.findViewById(R.id.authorTextView)

        titleTextView.text = book?.title
        authorTextView.text = book?.author

        return view
    }
}
