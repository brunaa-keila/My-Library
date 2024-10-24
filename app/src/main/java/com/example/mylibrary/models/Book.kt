package com.example.mylibrary.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    var title: String = "",
    var author: String = "",
    var summary: String = "",
    var imageUrl: String = "",
    var id: String = "" // Campo para o ID do livro no Firestore
) : Parcelable
