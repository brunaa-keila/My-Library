package com.BrunaKeila.CrudBiblioteca

import android.os.Parcel
import android.os.Parcelable

data class Livro(val id: Int?, val titulo: String?, val paginas: Int, val paginasLidas: Int, val tipo: String?, val autor: String?, ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(titulo)
        parcel.writeInt(paginas)
        parcel.writeInt(paginasLidas)
        parcel.writeString(tipo)
        parcel.writeString(autor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Livro> {
        override fun createFromParcel(parcel: Parcel): Livro {
            return Livro(parcel)
        }

        override fun newArray(size: Int): Array<Livro?> {
            return arrayOfNulls(size)
        }
    }
}